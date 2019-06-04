package crm.event;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class MemberCreateEvent {

  private String externalId;

  private String forename;

  private String surname;

  private String nickname;

  private String title;

  private String avatarExternalId;

  private Date birthday;

  private String email;

  private String password;

  private String cardNumber;

  private String phoneNumber;
}
