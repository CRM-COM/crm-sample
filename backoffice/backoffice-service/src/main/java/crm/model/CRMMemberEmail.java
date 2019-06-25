package crm.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CRMMemberEmail {

  private String type;

  @JsonProperty("email_address")
  private String emailAddress;

}
