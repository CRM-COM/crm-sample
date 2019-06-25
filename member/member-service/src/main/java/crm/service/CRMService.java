package crm.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import crm.config.CRMConfig;
import crm.entity.MemberIdentity;
import crm.event.MemberCreateEvent;
import crm.exception.MicroserviceException;
import crm.model.IdentityProvider;
import crm.model.crm.*;
import crm.repository.MemberIdentityRepository;
import crm.repository.MemberRepository;
import crm.utils.CRMErrorCodes;
import crm.utils.Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class CRMService {

  // 7000000 ms is a little bit less than 2 hours
  private static final long REFRESH_INTERVAL = 7000000;
  private static final long REFRESH_RETRY_INTERVAL = TimeUnit.SECONDS.toMillis(10);

  private final CRMConfig crmConfig;

  private final RestTemplate restTemplate;

  private final MemberRepository memberRepository;

  private final MemberIdentityRepository memberIdentityRepository;

  private final ObjectMapper mapper;

  private int tokensCount;

  private volatile String[] tokens;

  private Random random = new Random();

  public CRMService(CRMConfig crmConfig, RestTemplate restTemplate, MemberRepository memberRepository,
                    MemberIdentityRepository memberIdentityRepository) {
    this.crmConfig = crmConfig;
    this.restTemplate = restTemplate;
    this.mapper = new ObjectMapper();
    this.memberRepository = memberRepository;
    this.memberIdentityRepository = memberIdentityRepository;

    tokensCount = crmConfig.getTokensCount();
    tokens = new String[tokensCount];
//    refreshToken();
  }

  /**
   * From CRM.com docs:
   * "This method returns an authentication token which can subsequently be used by all other
   * Web API methods to access the system. Note that the authentication token expires after two hours"
   * <p>
   * See details in https://discover.crm.com/pages/viewpage.action?pageId=46342004
   */
  @Scheduled(fixedRate = REFRESH_INTERVAL, initialDelay = 0)
  protected void refreshToken() {
    while (crmConfig.isRefreshTokenActive()) {
      try {
        for (int i = 0; i < tokensCount; i++) {
          tokens[i] = authenticate().getData().getToken();
        }
        log.info("Tokens have been successfully refreshed. Tokens count: " + tokensCount);
        return;
      } catch (Exception e) {
        log.error("refreshToken failed", e);
        try {
          TimeUnit.MILLISECONDS.sleep(REFRESH_RETRY_INTERVAL);
        } catch (InterruptedException ignored) {
        }
      }
    }
  }

  public CRMAuthenticateResponse authenticate() {
    CRMAuthenticateRequest request = new CRMAuthenticateRequest(crmConfig.getUsername(),
        crmConfig.getPassword(), crmConfig.getOrganisation());
    String url = crmConfig.getUrl() + "/authentication/token";
    CRMAuthenticateResponse response = commonPostRequest(url, request,
        CRMAuthenticateResponse.class);
    return response;
  }

  private <RS extends CRMAdapterResponse> RS commonGetRequest(
      String url, Class<RS> responseClass) {
    URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
    RequestEntity<Void> entity = RequestEntity.method(HttpMethod.GET, uri)
//        .header(HttpHeaders.AUTHORIZATION, "Basic " + crmConfig.getKey())
        .header(HttpHeaders.CONTENT_TYPE, "application/json")
        .header(HttpHeaders.ACCEPT, "application/json")
        .header(HttpHeaders.ACCEPT_CHARSET, "utf-8")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .build();
    RS response = restTemplate.exchange(entity, responseClass).getBody();
    if (response != null && !response.isValid()) {
      log.info("Invalid Request: " + url);
      throw new MicroserviceException(Utils.jsonString(response.getStatus()));
    }

    return response;
  }

  @SneakyThrows
  private <RQ extends CRMAdapterRequest, RS extends CRMAdapterResponse> RS commonPostRequest(
      String url, RQ request, Class<RS> responseClass) throws MicroserviceException {
    URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
    RequestEntity<RQ> entity = RequestEntity.method(HttpMethod.POST, uri)
//        .header(HttpHeaders.AUTHORIZATION, "Basic " + crmConfig.getKey())
        .header(HttpHeaders.CONTENT_TYPE, "application/json")
        .header(HttpHeaders.ACCEPT, "application/json")
        .header(HttpHeaders.ACCEPT_CHARSET, "utf-8")
        .contentType(MediaType.APPLICATION_JSON_UTF8)
        .body(request);
    log.debug("Will call {} with body {}", url, mapper.writeValueAsString(request));
    RS response = restTemplate.exchange(entity, responseClass).getBody();
    if (response != null && !response.isValid()) {
      if (!CRMErrorCodes.getAllIgnoredCrmMessages().contains(response.getStatus().getCode())) {
        log.info("Invalid Request: " + Utils.jsonString(request) + ";;"
            + "Response: " + Utils.jsonString(response.getStatus()));
        throw new MicroserviceException(Utils.jsonString(response.getStatus()));
      }
    }

    return response;
  }

  private String getToken() {
    return tokensCount == 1 ? tokens[0] : tokens[random.nextInt(tokensCount)];
  }

  public void createMember(MemberCreateEvent event) {
    String url = crmConfig.getUrl() + "/contact_information/create";

    CRMCreateMemberRequest request = CRMCreateMemberRequest.builder()
        .token(getToken())
        .type("PERSON")
        .title(event.getTitle())
        .firstName(event.getForename())
        .lastName(event.getSurname())
        .demographics(CRMMemberDemographics.builder()
            .idNumber(uuidHexToUuid64(event.getExternalId()))
            .dateOfBirth(getBirthday(event.getBirthday()))
            .phones(Arrays.asList(new CRMMemberPhone("MOBILE", event.getPhoneNumber())))
            .emails(Arrays.asList(new CRMMemberEmail("PERSONAL", event.getEmail())))
            .build())
        .build();

    CRMStringResponse response = commonPostRequest(url, request, CRMStringResponse.class);

    saveCrmIdentity(event.getExternalId(), response.getData().getId());

    log.info("Member has been created {}", response);
  }

  private void saveCrmIdentity(String externalId, String crmId) {
      var member = memberRepository.findByExternalId(externalId)
              .orElseThrow(() -> new MicroserviceException("Member not found by externalId " + externalId));
      var crmIdentity = MemberIdentity.builder().identProvider(IdentityProvider.CRM).identValue(crmId).member(member).build();
      memberIdentityRepository.save(crmIdentity);
  }

  private CRMMemberDateOfBirth getBirthday(Date birthday) {
    if (birthday != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(birthday);

      return new CRMMemberDateOfBirth(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH) + 1,
          cal.get(Calendar.YEAR));
    } else {
      return null;
    }
  }

  public static String uuidHexToUuid64(String uuidStr) {
    UUID uuid = UUID.fromString(uuidStr);
    byte[] bytes = uuidToBytes(uuid);
    return Base64.getUrlEncoder().withoutPadding().encodeToString(bytes);
  }

  public static String uuid64ToUuidHex(String uuid64) {
    byte[] decoded = Base64.getUrlDecoder().decode(uuid64);
    UUID uuid = uuidFromBytes(decoded);
    return uuid.toString();
  }

  public static byte[] uuidToBytes(UUID uuid) {
    ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
    bb.putLong(uuid.getMostSignificantBits());
    bb.putLong(uuid.getLeastSignificantBits());
    return bb.array();
  }

  public static UUID uuidFromBytes(byte[] decoded) {
    ByteBuffer bb = ByteBuffer.wrap(decoded);
    long mostSigBits = bb.getLong();
    long leastSigBits = bb.getLong();
    return new UUID(mostSigBits, leastSigBits);
  }

  public CRMContactDetails getMember(String crmId) {
    return restTemplate.getForObject(crmConfig.getUrl() + "contact_information/show?token=" + getToken() +
            "&contact_information_identifier=id=" + crmId, CRMContactDetails.class);
  }
}

