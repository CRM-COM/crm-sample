package crm.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TechProviderDto {

    private String externalId;

    private String name;

    private String description;

    private String providerCode;
}
