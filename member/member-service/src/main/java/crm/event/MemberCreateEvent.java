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

  private String externalId;

  private String name;

  private String email;

  private String password;

  private String cardNumber;
}
