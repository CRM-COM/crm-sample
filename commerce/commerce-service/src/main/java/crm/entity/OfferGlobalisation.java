package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class OfferGlobalisation extends Globalisation {

    @ManyToOne
    private Offer offer;
}
