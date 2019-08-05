package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Entity @Getter
@Setter
public class PromotionSegment {

	@GeneratedValue
	@Id
    private int id;
 
	@ManyToOne(optional=false)
	private Segment segment;
	
	@ManyToOne(optional=false)
	private Promotion promotion;
}
