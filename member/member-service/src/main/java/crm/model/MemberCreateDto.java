package crm.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MemberCreateDto {

  private String forename;

  private String surname;

  @NotBlank
  private String nickname;

  private String title;

  private String avatarExternalId;

  @NotNull
  private Date birthday;

  @NotBlank
  private String email;

  private String password;

  private String cardNumber;

  private String phoneNumber;

}
