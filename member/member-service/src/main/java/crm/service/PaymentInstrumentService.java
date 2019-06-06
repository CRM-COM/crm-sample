package crm.service;

import crm.config.PaymentInstrumentStream;
import crm.entity.PaymentInstrument;
import crm.event.PaymentInstrumentCreateEvent;
import crm.model.PaymentInstrumentCreateDto;
import crm.model.PaymentInstrumentDto;
import crm.repository.PaymentInstrumentRepository;
import crm.security.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.util.MimeTypeUtils;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PaymentInstrumentService {

    private final PaymentInstrumentStream stream;
    private final PaymentInstrumentRepository repository;
    private final JwtService jwtService;

    public void addPaymentInstrument(String token, PaymentInstrumentCreateDto paymentInstrument) {
        var memberExternalId = jwtService.parseToken(token).getExternalId();
        var messageChannel = stream.outboundaymentInstrument();
        var event = createEvent(paymentInstrument, memberExternalId);
        messageChannel.send(MessageBuilder.withPayload(event)
                .setHeader(MessageHeaders.CONTENT_TYPE, MimeTypeUtils.APPLICATION_JSON)
                .build());
        log.info("Sending payment instrument create event {}", event.getExternalId());
    }

    private PaymentInstrumentCreateEvent createEvent(PaymentInstrumentCreateDto paymentInstrument, String memberExternalId) {
        String externalId = UUID.randomUUID().toString();
        return PaymentInstrumentCreateEvent
                .builder()
                .externalId(externalId)
                .friendlyName(paymentInstrument.getFriendlyName())
                .instrumentType(paymentInstrument.getInstrumentType())
                .obsfucated(paymentInstrument.getObsfucated())
                .tokenised(paymentInstrument.getTokenised())
                .memberExternalId(memberExternalId)
                .build();
    }

    public List<PaymentInstrumentDto> getPaymentInstruments(String token) {
        var memberExternalId = jwtService.parseToken(token).getExternalId();
        return repository.findByMemberExternalId(memberExternalId).stream().map(this::toDto).collect(Collectors.toList());
    }

    private PaymentInstrumentDto toDto(PaymentInstrument paymentInstrument) {
        return PaymentInstrumentDto.builder()
                .externalId(paymentInstrument.getExternalId())
                .friendlyName(paymentInstrument.getFriendlyName())
                .instrumentType(paymentInstrument.getInstrumentType())
                .obsfucated(paymentInstrument.getObsfucated())
                .tokenised(paymentInstrument.getTokenised())
                .build();
    }
}
