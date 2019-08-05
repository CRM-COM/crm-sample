package crm.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@NoArgsConstructor
@AllArgsConstructor
@Entity @Getter
@Setter
@Builder
public class OfferPromotion {

	@GeneratedValue
	@Id
    private Integer id;
 
	@ManyToOne(optional=false)
	private Offer offer;
	
	@ManyToOne(optional=false)
	private Promotion promotion;

}
