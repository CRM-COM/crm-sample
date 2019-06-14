package crm.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserOrganisationDto {

  @NotBlank
  private String organisationId;
  @NotBlank
  private String roleId;
}
