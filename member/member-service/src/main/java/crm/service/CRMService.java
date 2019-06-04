package crm.service;

import java.net.URI;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.fasterxml.jackson.databind.ObjectMapper;

import crm.config.CRMConfig;
import crm.event.MemberCreateEvent;
import crm.exception.MicroserviceException;
import crm.model.crm.CRMAdapterRequest;
import crm.model.crm.CRMAdapterResponse;
import crm.model.crm.CRMAuthenticateRequest;
import crm.model.crm.CRMAuthenticateResponse;
import crm.model.crm.CRMCreateMemberRequest;
import crm.model.crm.CRMMemberDateOfBirth;
import crm.model.crm.CRMMemberDemographics;
import crm.model.crm.CRMMemberEmail;
import crm.model.crm.CRMMemberPhone;
import crm.model.crm.CRMStringResponse;
import crm.utils.CRMErrorCodes;
import crm.utils.Utils;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class CRMService {

  // 7000000 ms is a little bit less than 2 hours
  private static final long REFRESH_INTERVAL = 7000000;
  private static final long REFRESH_RETRY_INTERVAL = TimeUnit.SECONDS.toMillis(10);

  private final CRMConfig crmConfig;

  private final RestTemplate restTemplate;

  private final ObjectMapper mapper;

  private int tokensCount;

  private volatile String[] tokens;

  private Random random = new Random();

  public CRMService(CRMConfig crmConfig, RestTemplate restTemplate,
      ObjectMapper mapper) {
    this.crmConfig = crmConfig;
    this.restTemplate = restTemplate;
    this.mapper = mapper;

    tokensCount = crmConfig.getTokensCount();
    tokens = new String[tokensCount];
    refreshToken();
  }

  /**
   * From CRM.com docs:
   * "This method returns an authentication token which can subsequently be used by all other
   * Web API methods to access the system. Note that the authentication token expires after two hours"
   * <p>
   * See details in https://discover.crm.com/pages/viewpage.action?pageId=46342004
   */
  @Scheduled(fixedRate = REFRESH_INTERVAL, initialDelay = REFRESH_INTERVAL)
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
            .dateOfBirth(getBirthday(event.getBirthday()))
            .phones(Arrays.asList(new CRMMemberPhone("MOBILE", event.getPhoneNumber())))
            .emails(Arrays.asList(new CRMMemberEmail("PERSONAL", event.getEmail())))
            .build())
        .build();

    CRMStringResponse response = commonPostRequest(url, request, CRMStringResponse.class);

    log.info("Member has been created: " + response);
  }

  private CRMMemberDateOfBirth getBirthday(Date birthday) {
    if (birthday != null) {
      Calendar cal = Calendar.getInstance();
      cal.setTime(birthday);

      return new CRMMemberDateOfBirth(cal.get(Calendar.DAY_OF_MONTH), cal.get(Calendar.MONTH),
          cal.get(Calendar.YEAR));
    } else {
      return null;
    }
  }
}

