package crm.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class OfferPriceDto {

	private String iso4127CurrencyCode;

	private String iso3166CountryCode;

	private String name;

	private Double price;

	private Double tax;

	private Set<Quality> quality;

	private String priceExternalReference;
	
	private TechProviderDto platform;

	private String currencySymbol;
}