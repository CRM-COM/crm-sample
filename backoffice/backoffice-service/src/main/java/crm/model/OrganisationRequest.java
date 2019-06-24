package crm.model;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrganisationRequest {

  @NotBlank
  private String email;
  @NotBlank
  private String password;
  @NotBlank
  private String forename;
  @NotBlank
  private String surname;
  @NotBlank
  private String organisationName;
  private String organisationDescription;
  private String vatId;
  private String fullAddress;
  private String areaCode;
  private String zipPostcode;
  private String countryCode;
  private BigDecimal latitude;
  private BigDecimal longitude;
}
