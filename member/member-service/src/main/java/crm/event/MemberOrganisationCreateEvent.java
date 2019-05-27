package crm.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberOrganisationCreateEvent {

  private String memberExternalId;

  private String organisationExternalId;

  private String organisationName;
}
