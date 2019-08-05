package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter
@Setter
@Entity
public class OfferFeatures {

    @GeneratedValue
    @Id
    private Long id;

    private String language;

    private String name;

    private String description;

    @ManyToOne
    private Offer offer;

    private int sequence;
}
