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
import org.keycloak.KeycloakPrincipal;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
    return new MemberDto(member.getExternalId(), member.getForename(), member.getSurname(), member.getNickname(), member.getTitle());
  }

  public MemberDto getMember(String token) {
    var memberExternalId = jwtService.parseToken(token);
    return memberRepository.findByExternalId(memberExternalId)
            .map(this::toDto)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member"));
  }

  public Token authenticate(AuthenticationDto authDto) {
    var token = keycloakService.auth(authDto);
    jwtService.decode(token.getAccessToken());
    String externalId = memberRepository.findByNickname(authDto.getUsername())
            .map(Member::getExternalId)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member"));

    return jwtService.createToken(externalId);
  }

  private void checkPassword(String password, String loginPassword) {
    if(!passwordEncoder.matches(loginPassword, password))
      throw new MicroserviceException(HttpStatus.UNAUTHORIZED, "");
  }
}
