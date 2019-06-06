package crm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentInstrumentDto {

    private InstrumentType instrumentType;

    private String friendlyName;

    private String obsfucated;

    private String tokenised;
}
