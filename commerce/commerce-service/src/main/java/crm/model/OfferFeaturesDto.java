package crm.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class OfferFeaturesDto {
    @JsonIgnore
    private String language;
    private String name;
    private String description;
}
