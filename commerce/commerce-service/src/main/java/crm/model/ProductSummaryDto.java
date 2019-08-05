package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductSummaryDto {

    private String externalId;
    private String name;
    private String description;
}
