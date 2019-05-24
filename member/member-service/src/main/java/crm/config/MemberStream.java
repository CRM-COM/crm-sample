package crm.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface MemberStream {

    String INPUT = "member-in";
    String OUTPUT = "member-out";

    @Input(INPUT)
    SubscribableChannel inboundMember();

    @Output(OUTPUT)
    MessageChannel outboundMember();
}
