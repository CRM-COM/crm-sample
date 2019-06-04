package crm.model.crm;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class CRMMemberDateOfBirth {

  private int day;

  private int month;

  private int year;
}
