package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductGlobalisationDto {
    private String language;
    private String name;
    private String description;
}
