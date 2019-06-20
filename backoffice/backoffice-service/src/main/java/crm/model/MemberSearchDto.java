package crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberSearchDto {

  private String externalId;

  private String forename;

  private String surname;

  private String nickname;

  private String title;

  private String crmId;

  private String avatar;
}
