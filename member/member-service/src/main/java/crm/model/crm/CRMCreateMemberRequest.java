package crm.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMCreateMemberRequest extends CRMAdapterRequest {

    private String type;

    private String title;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private CRMMemberDemographics demographics;

    @Builder
    public CRMCreateMemberRequest(String token, String type, String title, String firstName,
        String lastName, CRMMemberDemographics demographics) {
        super(token);
        this.type = type;
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.demographics = demographics;
    }
}
