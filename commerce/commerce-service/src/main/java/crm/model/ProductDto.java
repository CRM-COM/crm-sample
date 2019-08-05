package crm.model;

import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProductDto {

    private String externalId;

    private Date createdOn;

    private Date modifiedOn;

    private String name;

    private String description;

    @Builder.Default
    private List<OfferDto> offers = new ArrayList<>();

    private String productSku;
}
