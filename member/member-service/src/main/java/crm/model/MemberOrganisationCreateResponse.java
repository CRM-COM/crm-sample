package crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberOrganisationCreateResponse {

  private String memberExternalId;

  private String organisationExternalId;

  private String organisationName;
}
