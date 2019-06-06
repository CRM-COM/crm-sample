package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstrumentCreateDto {

    private InstrumentType instrumentType;

    private String friendlyName;

    private String obsfucated;

    private String tokenised;
}
