package crm.model.crm;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
