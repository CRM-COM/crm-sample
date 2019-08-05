package crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OfferDto {

    private String externalId;

    private Date createdOn;

    private Date modifiedOn;

    private String name;

    private String offerCode;

    private String description;

    private String externalReference;

    private OfferType type;

    private boolean eligibleForPromotion;

    @Builder.Default
    private List<OfferPriceDto> prices = new ArrayList<>();

    @Builder.Default
    private List<PromotionDto> promotions = new ArrayList<>();

    @JsonIgnore
    @Builder.Default
    private List<OfferGlobalisationDto> offerGlobalisations = new ArrayList<>();

    @Builder.Default
    private List<OfferFeaturesDto> offerFeatures = new ArrayList<>();

    private OfferEntitlementDto enablement;

}
