package crm.model;

import crm.entity.Quality;
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

    private Quality quality;

    private String priceExternalReference;

    private String currencySymbol;
}