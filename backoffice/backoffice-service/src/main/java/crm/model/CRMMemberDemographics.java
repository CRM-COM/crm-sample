package crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CRMMemberDemographics {

  private String gender;

  @JsonProperty("id_number")
  private String idNumber;

  @JsonProperty("date_of_birth")
  private CRMMemberDateOfBirth dateOfBirth;

  @JsonProperty("phone_set")
  private List<CRMMemberPhone> phones;

  @JsonProperty("email_set")
  private List<CRMMemberEmail> emails;
}
