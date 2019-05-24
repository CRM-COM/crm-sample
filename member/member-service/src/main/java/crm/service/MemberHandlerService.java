package crm.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import crm.config.MemberStream;
import crm.entity.Member;
import crm.event.MemberCreateEvent;
import crm.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberHandlerService {

  private final MemberRepository memberRepository;

  private final BCryptPasswordEncoder passwordEncoder;

  @StreamListener(MemberStream.INPUT)
  public void handleMember(@Payload MemberCreateEvent memberEvent) {
    log.info("Received member create event for id: {}", memberEvent.getExternalId());

    Member member = new Member(memberEvent.getExternalId(), memberEvent.getName(),
        memberEvent.getEmail(), passwordEncoder.encode(memberEvent.getPassword()));

    memberRepository.save(member);
  }
}
