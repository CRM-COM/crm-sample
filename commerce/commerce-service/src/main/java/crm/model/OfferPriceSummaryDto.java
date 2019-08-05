package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferPriceSummaryDto {

    private String externalId;
    private String name;
}
