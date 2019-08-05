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
public class ProductPlatform {

    @GeneratedValue
    @Id
    private long id;

    private String platformExternalId;

    @ManyToOne
    private Product product;

    private int sequence;
}
