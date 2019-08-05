package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Getter
@Setter
public class PromotionGlobalisation extends Globalisation {

    @ManyToOne
    private Promotion promotion;
}
