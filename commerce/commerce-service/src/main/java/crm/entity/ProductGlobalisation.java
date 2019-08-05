package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class ProductGlobalisation extends Globalisation {

    @ManyToOne
    private Product product;
}
