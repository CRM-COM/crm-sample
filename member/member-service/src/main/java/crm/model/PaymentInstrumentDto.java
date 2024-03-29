package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstrumentDto {

    private String externalId;

    private InstrumentType instrumentType;

    private String friendlyName;

    private String obsfucated;

    private String tokenised;
}
