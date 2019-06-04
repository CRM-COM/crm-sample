package crm.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {

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
