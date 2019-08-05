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
public class PromotionDto {

    private String externalId;

    private String name;

    private String description;

    private Double percentage;

    private Double fixedPrice;

    private Date startDate;

    private Date endDate;

    private Integer extraInstances;

    private String coupon;

    private String iso4127CurrencyCode;

    @JsonIgnore
    @Builder.Default
    private List<PromotionGlobalisationDto> promotionGlobalisations = new ArrayList<>();
}
