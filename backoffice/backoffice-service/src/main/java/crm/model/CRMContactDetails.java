package crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMContactDetails {

    private String type;
    private String id;
    private String name;

    @JsonProperty("emails_set")
    private List<CRMContactDetailsEmail> emails = new ArrayList<>();

    @JsonProperty("preferred_language")
    private String preferredLanguage;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("last_name")
    private String lastName;

    private CRMMemberDemographics demographics;
}
