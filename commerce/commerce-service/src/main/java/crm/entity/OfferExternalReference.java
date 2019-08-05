package crm.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@Entity
public class OfferExternalReference {

	@GeneratedValue
	@Id
    private int id;
 
	@ManyToOne(optional=false)
	private Offer offer;
	
	@ManyToOne(optional=false)
	private TechProvider provider;
	
	@Column(length=512, nullable=false)
	private String referenceId;

	@Column(name = "PRODUCT_SKU")
	private String productSku;
}
