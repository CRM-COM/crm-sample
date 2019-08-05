package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OfferSummaryDto {

    private String externalId;
    private String name;
}
