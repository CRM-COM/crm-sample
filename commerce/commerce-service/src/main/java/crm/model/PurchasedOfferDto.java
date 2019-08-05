package crm.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PurchasedOfferDto {

    private String externalReference;

    private String productSku;

    private PeriodType validityPeriodType;

    private PeriodType freePeriodType;
    
    private boolean isMultipack;
    
    private Integer validityPeriod;

    private Integer freePeriod;

    private Integer maxInstances;
    
    private String productExternalId;
    
    private String offerExternalId;

    private String offerName;

    public PurchasedOfferDto(String externalReference, String productSku, PeriodType validityPeriodType,
                             PeriodType freePeriodType, boolean isMultipack, Integer validityPeriod, Integer
                                     freePeriod, Integer maxInstances, String productExternalId, String offerExternalId) {
        this.externalReference = externalReference;
        this.productSku = productSku;
        this.validityPeriodType = validityPeriodType;
        this.freePeriodType = freePeriodType;
        this.isMultipack = isMultipack;
        this.validityPeriod = validityPeriod;
        this.freePeriod = freePeriod;
        this.maxInstances = maxInstances;
        this.productExternalId = productExternalId;
        this.offerExternalId = offerExternalId;
    }
}