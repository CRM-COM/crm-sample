package crm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class OfferPriceDto {

	private String iso4127CurrencyCode;

	private String iso3166CountryCode;

	private String name;

	private Double price;

	private Double tax;

	private String quality;

	private String priceExternalReference;
	
	private TechProviderDto platform;

	private String currencySymbol;
}