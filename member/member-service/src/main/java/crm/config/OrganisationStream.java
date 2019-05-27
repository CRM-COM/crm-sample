package crm.config;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface OrganisationStream {

    String INPUT = "organisation-in";
    String OUTPUT = "organisation-out";

    @Input(INPUT)
    SubscribableChannel inboundOrganisation();

    @Output(OUTPUT)
    MessageChannel outboundOrganisation();
}
