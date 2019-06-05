package crm.service;

import crm.config.MemberStream;
import crm.config.OrganisationStream;
import crm.event.MemberCreateEvent;
import crm.event.MemberOrganisationCreateEvent;
import crm.exception.MicroserviceException;
import crm.model.MemberCreateDto;
import crm.model.MemberOrganisationCreateDto;
import crm.model.MemberOrganisationCreateResponse;
import crm.security.JwtService;
import crm.security.Token;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberOutService {

  private final MemberStream memberStream;
  private final OrganisationStream organisationStream;
  private final JwtService jwtService;
  private final RedisService redisService;

  public Token addMember(MemberCreateDto member) {
    checkIfExists(member);
    String externalId = UUID.randomUUID().toString();
    log.info("Sending member create event {}", externalId);

    var messageChannel = memberStream.outboundMember();
    messageChannel.send(MessageBuilder.withPayload(MemberCreateEvent.builder()
            .externalId(externalId)
            .forename(member.getForename())
            .surname(member.getSurname())
            .nickname(member.getNickname())
            .title(member.getTitle())
            .avatarExternalId(member.getAvatarExternalId())
            .birthday(member.getBirthday())
            .email(member.getEmail())
            .password(member.getPassword())
            .cardNumber(member.getCardNumber())
            .phoneNumber(member.getPhoneNumber())
            .build())
            .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
            .build());

    return jwtService.createToken(externalId, null);
  }

  private void checkIfExists(MemberCreateDto member) {
    if(redisService.existsByEmail(member.getEmail()))
      throw new MicroserviceException("Email " + member.getEmail() + " exists");

    if(redisService.existsByNickname(member.getNickname()))
      throw new MicroserviceException("Nickname " + member.getNickname() + " exists");
  }

  public MemberOrganisationCreateResponse addOrganisation(String memberId,
      MemberOrganisationCreateDto organisationCreateDto) {
    String organisationId = UUID.randomUUID().toString();

    log.info("Sending member organisation create event for member with id {}", memberId);

    var messageChannel = organisationStream.outboundOrganisation();

    messageChannel.send(MessageBuilder.withPayload(MemberOrganisationCreateEvent.builder()
        .memberExternalId(memberId)
        .organisationName(organisationCreateDto.getName())
        .organisationExternalId(organisationId)
        .build())
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());

    return new MemberOrganisationCreateResponse(memberId, organisationId,
        organisationCreateDto.getName());
  }
}
