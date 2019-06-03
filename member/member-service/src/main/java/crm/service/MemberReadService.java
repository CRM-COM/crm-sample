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

  public MemberDto getMember() {
    String externalId = ((KeycloakPrincipal)SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getName();
    return memberRepository.findByExternalId(externalId)
            .map(this::toDto)
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member"));
  }
}
