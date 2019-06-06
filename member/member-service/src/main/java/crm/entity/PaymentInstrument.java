package crm.entity;

import crm.model.InstrumentType;
import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentInstrument {

    @Id
    @GeneratedValue
    private long id;

    private String externalId;

    @Enumerated(EnumType.STRING)
    private InstrumentType instrumentType;

    private String friendlyName;

    private String obsfucated;

    private String tokenised;

    @ManyToOne
    @JoinColumn
    private Member member;
}
