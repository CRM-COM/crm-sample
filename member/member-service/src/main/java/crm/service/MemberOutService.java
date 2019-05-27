package crm.service;

import java.util.UUID;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import crm.config.OrganisationStream;
import crm.event.MemberOrganisationCreateEvent;
import crm.model.MemberOrganisationCreateDto;
import crm.model.MemberOrganisationCreateResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import crm.config.MemberStream;
import crm.event.MemberCreateEvent;
import crm.model.MemberCreateDto;
import crm.model.MemberResponseDto;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberOutService {

  private final MemberStream memberStream;
  private final OrganisationStream organisationStream;

  public MemberResponseDto addMember(MemberCreateDto member) {
    String externalId = UUID.randomUUID().toString();
    log.info("Sending member create event {}", externalId);

    var messageChannel = memberStream.outboundMember();
    messageChannel.send(MessageBuilder.withPayload(MemberCreateEvent.builder()
        .externalId(externalId)
        .name(member.getName())
        .email(member.getEmail())
        .password(member.getPassword())
        .cardNumber(member.getCardNumber())
        .provider(member.getProvider())
        .build())
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());

    return new MemberResponseDto(externalId, member.getName(), member.getEmail());
  }

  public MemberOrganisationCreateResponse addOrganisation(String memberId,
      MemberOrganisationCreateDto organisationCreateDto) {
    String organisationId = UUID.randomUUID().toString();

    log.info("Sending member organisation create event for member with id ", memberId);

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
