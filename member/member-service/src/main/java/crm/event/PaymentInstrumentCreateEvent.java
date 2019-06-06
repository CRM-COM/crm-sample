package crm.event;

import crm.model.InstrumentType;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstrumentCreateEvent {

    private String externalId;

    private InstrumentType instrumentType;

    private String friendlyName;

    private String obsfucated;

    private String tokenised;

    private String memberExternalId;
}
