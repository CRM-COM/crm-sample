package crm.service;

import crm.entity.Member;
import crm.entity.MemberIdentity;
import crm.exception.MicroserviceException;
import crm.model.AuthenticationDto;
import crm.model.IdentityProvider;
import crm.model.MemberDto;
import crm.model.Token;
import crm.repository.MemberIdentityRepository;
import crm.repository.MemberRepository;
import crm.security.JwtService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
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
      MemberIdentity identity = identityRepository.findByCardNumber(idOrCard)
          .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND,
              "Cannot find member with card number " + idOrCard));
      return toDto(identity.getMember());
    }
  }

  private MemberDto toDto(Member member) {
    return new MemberDto(member.getExternalId(), member.getName(), member.getEmail());
  }

  public Token authenticate(AuthenticationDto authDto) {
    var member = memberRepository.findByEmail(authDto.getEmail())
            .orElseThrow(() -> new MicroserviceException(HttpStatus.NOT_FOUND, "Cannot find member with email " + authDto.getEmail()));
    var password = getPassword(member);
    checkPassword(password, authDto.getPassword());

    var accessToken = jwtService.createToken(member.getExternalId());
    return Token.builder().accessToken(accessToken).build();
  }

  private void checkPassword(String password, String loginPassword) {
    if(!passwordEncoder.matches(loginPassword, password))
      throw new MicroserviceException(HttpStatus.UNAUTHORIZED, "");
  }

  private String getPassword(Member member) {
    return member.getMemberIdentities().stream()
            .filter(identity -> identity.getProvider().equals(IdentityProvider.PASSWORD))
            .map(MemberIdentity::getIdentValue)
            .findFirst()
            .orElse(null);
  }
}
