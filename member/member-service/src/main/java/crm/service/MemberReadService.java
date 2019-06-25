package crm.service;

import crm.entity.Member;
import crm.entity.MemberIdentity;
import crm.exception.MicroserviceException;
import crm.model.AuthenticationDto;
import crm.model.IdentityProvider;
import crm.model.MemberDto;
import crm.model.crm.CRMContactDetails;
import crm.repository.MemberIdentityRepository;
import crm.repository.MemberRepository;
import crm.security.JwtService;
import crm.security.Token;
import io.micrometer.core.instrument.util.StringUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;
import java.util.function.Function;

@Service
@RequiredArgsConstructor
public class MemberReadService {

  private final MemberRepository memberRepository;
  private final MemberIdentityRepository identityRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final KeycloakService keycloakService;
  private final CRMService crmService;

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
      return MemberDto.builder()
              .externalId(member.getExternalId())
              .forename(member.getForename())
              .surname(member.getSurname())
              .nickname(member.getNickname())
              .title(member.getTitle())
              .avatar(member.getAvatarExternalId())
              .crmId(getIdentityValue(member.getMemberIdentities(), IdentityProvider.CRM))
              .phone(getIdentityValue(member.getMemberIdentities(), IdentityProvider.PHONE))
              .email(getIdentity(member.getMemberIdentities(), IdentityProvider.PASSWORD, MemberIdentity::getIdentChallenge))
              .build();
  }

    private String getIdentity(Set<MemberIdentity> identities, IdentityProvider provider, Function<MemberIdentity, String> getValue) {
        return identities.stream()
                .filter(identity -> isProvider(provider, identity))
                .findFirst()
                .map(getValue)
                .orElse(null);
    }

    private boolean isProvider(IdentityProvider provider, MemberIdentity identity) {
        return identity.getIdentProvider().equals(provider);
    }

    private String getIdentityValue(Set<MemberIdentity> memberIdentities, IdentityProvider provider) {
      return getIdentity(memberIdentities, provider, MemberIdentity::getIdentValue);
    }

  public MemberDto getMember(String token) {
    var decoded = jwtService.parseToken(token);
    return memberRepository.findByExternalId(decoded.getExternalId())
            .map(this::toDto)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member"));
  }

  public Token authenticate(AuthenticationDto authDto) {
    var token = keycloakService.auth(authDto);
    var decodedToken = jwtService.decodeKeycloakToken(token.getAccessToken());

    return jwtService.createToken(decodedToken.getExternalId(), decodedToken.getKeycloakExternalId());
  }

  public Page<MemberDto> search(String criteria, String query, Pageable pageable) {
      if(StringUtils.isBlank(criteria) || StringUtils.isBlank(query))
          return memberRepository.findAll(pageable).map(this::toDto);
      query = query.toLowerCase();
      final var searchFields = criteria.split(",");
      String forename = null, surname = null, nickname = null, email = null;
      for(var field : searchFields) {
          switch (field) {
              case "forename": forename = query; break;
              case "surname": surname = query; break;
              case "nickname": nickname = query; break;
              case "email": email = query; break;
          }
      }
      return memberRepository.search(forename, surname, nickname, email, pageable).map(this::toDto);
  }

    public CRMContactDetails getCrmMember(String externalId) {
      return memberRepository.findByExternalId(externalId)
              .map(Member::getMemberIdentities)
              .map(identities -> getIdentityValue(identities, IdentityProvider.CRM))
              .map(crmService::getMember)
              .orElseThrow(() -> new MicroserviceException(HttpStatus.BAD_REQUEST, "Member not found"));
    }
}
