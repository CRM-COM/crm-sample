package crm.service;

import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import crm.config.MemberCRMStream;
import crm.event.MemberCreateEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class MemberCRMHandlerService {

  private final CRMService crmService;

  @StreamListener(MemberCRMStream.INPUT)
  public void handleMember(@Payload MemberCreateEvent memberEvent) {
    log.info("CRM Received member create event for id: {}", memberEvent);

    crmService.createMember(memberEvent);
  }
}
