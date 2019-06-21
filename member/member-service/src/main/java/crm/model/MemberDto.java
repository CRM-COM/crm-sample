package crm.model;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MemberDto {

  private String externalId;

  private String forename;

  private String surname;

  private String nickname;

  private String title;

  private String crmId;

  private String avatar;

  private String email;

  private String phone;
}
