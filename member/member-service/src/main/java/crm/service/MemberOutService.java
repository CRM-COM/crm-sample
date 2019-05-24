package crm.service;

import java.util.UUID;

import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

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

  public MemberResponseDto addMember(MemberCreateDto member) {
    String externalId = UUID.randomUUID().toString();
    log.info("Sending member create event {}", externalId);

    var messageChannel = memberStream.outboundMember();
    messageChannel.send(MessageBuilder.withPayload(MemberCreateEvent.builder()
        .externalId(externalId)
        .name(member.getName())
        .email(member.getEmail())
        .password(member.getPassword())
        .build())
        .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
        .build());

    return new MemberResponseDto(externalId, member.getName(), member.getEmail());
  }
}
