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
import crm.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MemberReadService {

  private final MemberRepository memberRepository;
  private final MemberIdentityRepository identityRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final KeycloakService keycloakService;

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
    return new MemberDto(member.getExternalId(), member.getForename(), member.getSurname(),
        member.getNickname(), member.getTitle(), getCrmId(member.getMemberIdentities()));
  }

  private String getCrmId(Set<MemberIdentity> memberIdentities) {
    return memberIdentities.stream()
        .filter(identity -> identity.getIdentProvider().equals(IdentityProvider.CRM))
        .findFirst().map(MemberIdentity::getIdentValue).orElse(null);
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
}
