package crm.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface PaymentInstrumentStream {

    String INPUT = "payment-instrument-in";
    String OUTPUT = "payment-instrument-out";

    @Input(INPUT)
    SubscribableChannel inboundPaymentInstrument();

    @Output(OUTPUT)
    MessageChannel outboundaymentInstrument();
}
