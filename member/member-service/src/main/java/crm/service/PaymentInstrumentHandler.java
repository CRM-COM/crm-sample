package crm.service;

import crm.config.PaymentInstrumentStream;
import crm.entity.Member;
import crm.entity.PaymentInstrument;
import crm.event.PaymentInstrumentCreateEvent;
import crm.exception.MicroserviceException;
import crm.repository.MemberRepository;
import crm.repository.PaymentInstrumentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentInstrumentHandler {

    private final MemberRepository memberRepository;
    private final PaymentInstrumentRepository paymentInstrumentRepository;

    @StreamListener(PaymentInstrumentStream.INPUT)
    public void handleMember(@Payload PaymentInstrumentCreateEvent event) {
        log.info("Received payment instrument create event for id: {}", event);
        var member = memberRepository.findByExternalId(event.getMemberExternalId())
                .orElseThrow(() -> new MicroserviceException("Member not fount " + event.getMemberExternalId()));
        var paymentInstrument = toPaymentInstrument(event, member);
        paymentInstrumentRepository.save(paymentInstrument);
    }

    private PaymentInstrument toPaymentInstrument(PaymentInstrumentCreateEvent event, Member member) {
        return PaymentInstrument.builder()
                .externalId(event.getExternalId())
                .friendlyName(event.getFriendlyName())
                .instrumentType(event.getInstrumentType())
                .obsfucated(event.getObsfucated())
                .tokenised(event.getTokenised())
                .member(member)
                .build();
    }

}
