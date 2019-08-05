package crm.model;

import crm.entity.OfferType;
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

}
