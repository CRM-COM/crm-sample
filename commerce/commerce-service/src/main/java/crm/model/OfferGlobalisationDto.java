package crm.model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OfferGlobalisationDto {
    private String language;
    private String name;
    private String description;
}
