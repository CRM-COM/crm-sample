package crm.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DecodedToken {
    @JsonProperty("sub")
    private String externalId;
}
