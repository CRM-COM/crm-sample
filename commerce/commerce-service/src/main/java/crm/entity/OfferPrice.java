package crm.entity;

import crm.model.Quality;
import lombok.*;
import org.hibernate.annotations.ResultCheckStyle;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@Entity
@SQLDelete(sql = "UPDATE offer_price SET is_deleted = 1 WHERE id = ?", check = ResultCheckStyle.COUNT)
@Where(clause = "is_deleted=0")
public class OfferPrice extends AuditBase {

	@GeneratedValue 
	@Id
    private Long id;
	
	@Column(nullable=false, length=3)
	private String iso4127CurrencyCode;

	@Column(length=3)
	private String iso3166CountryCode;

	@Column(nullable=false, length=128)
	private String name;

	@Column(nullable=false)
	private Double price;

	private Double tax;

	@Enumerated(EnumType.STRING)
	private Quality quality;
	
	@Column(length=64)
	private String priceExternalReference;
	
	@ManyToOne(optional=true)
	private TechProvider platform;
	
	@ManyToOne
	private Offer offer;

	public static Collection<String> getIso4127CurrencyCodes(Set<Offer> offers) {
		return offers.stream()
				.flatMap(it -> it.getPrices().stream())
				.map(OfferPrice::getIso4127CurrencyCode)
				.collect(Collectors.toSet());
	}
}
