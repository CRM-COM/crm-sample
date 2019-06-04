package crm.model.crm;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class CRMStringResponse extends CRMAdapterResponse {

  private String data;
}
