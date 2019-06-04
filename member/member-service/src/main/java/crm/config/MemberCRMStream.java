package crm.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MemberCRMStream {

    String INPUT = "member-crm-in";
    String OUTPUT = "member-crm-out";

    @Input(INPUT)
    SubscribableChannel inboundMember();

    @Output(OUTPUT)
    MessageChannel outboundMember();
}
