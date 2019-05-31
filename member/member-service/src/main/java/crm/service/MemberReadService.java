package crm.service;

import crm.entity.Member;
import crm.entity.MemberIdentity;
import crm.exception.MicroserviceException;
import crm.model.AuthenticationDto;
import crm.model.IdentityProvider;
import crm.model.MemberDto;
import crm.repository.MemberIdentityRepository;
import crm.repository.MemberRepository;
import crm.security.JwtService;
import crm.security.KeyCloakToken;
import crm.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberReadService {

  private final MemberRepository memberRepository;
  private final MemberIdentityRepository identityRepository;
  private final PasswordEncoder passwordEncoder;
  private final RestTemplate restTemplate;
  private final JwtService jwtService;

  public MemberDto getMemberByIdOrCard(String idOrCard) {
    try {
      String id = UUID.fromString(idOrCard).toString();
      Member member = memberRepository.findByExternalId(id)
          .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND,
              "Cannot find member with id " + id));
      return toDto(member);
    } catch (IllegalArgumentException e) {
      MemberIdentity identity = identityRepository.findByIdentValueAndIdentProvider(idOrCard, IdentityProvider.CREDIT_CARD)
          .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND,
              "Cannot find member with card number " + idOrCard));
      return toDto(identity.getMember());
    }
  }

  private MemberDto toDto(Member member) {
    return new MemberDto(member.getExternalId(), member.getForename(), member.getSurname(), member.getNickname(), member.getTitle());
  }

  public KeyCloakToken auth(AuthenticationDto authDto) {
    String plainCreds = "crm-dev:08e7e73a-6fb2-41ff-88c9-6178300f7b2a";
    var basicAuth = Base64.getEncoder().encodeToString(plainCreds.getBytes());
    var headers = new HttpHeaders();
    headers.add("Authorization", "Basic " + basicAuth);
    headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

    var map = new LinkedMultiValueMap<String, String>();
    map.add("username", authDto.getEmail());
    map.add("password", authDto.getPassword());
    map.add("grant_type", "password");

    var request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

    return restTemplate.postForObject("https://keycloak.crmcloudapi.com/auth/realms/crm-dev/protocol/openid-connect/token", request, KeyCloakToken.class);
  }

  public Token authenticate(AuthenticationDto authDto) {
    var identity = identityRepository.findByIdentChallengeAndIdentProvider(authDto.getEmail(), IdentityProvider.PASSWORD)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member with email " + authDto.getEmail()));
    checkPassword(identity.getIdentValue(), authDto.getPassword());

    return jwtService.createToken(identity.getMember().getExternalId());
  }

  private void checkPassword(String password, String loginPassword) {
    if(!passwordEncoder.matches(loginPassword, password))
      throw new MicroserviceException(HttpStatus.UNAUTHORIZED, "");
  }

  public MemberDto getMember(String token) {
    var memberExternalId = jwtService.parseToken(token);
    return memberRepository.findByExternalId(memberExternalId)
            .map(this::toDto)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member"));
  }
}
