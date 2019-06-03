package crm.event;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberCreateEvent {

  private String forename;

  private String surname;

  private String nickname;

  private String title;

  private String avatarExternalId;


  private String email;

  private String password;

  private String cardNumber;

  private String phoneNumber;
}
