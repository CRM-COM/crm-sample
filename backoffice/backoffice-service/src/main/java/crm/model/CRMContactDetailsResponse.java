package crm.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMContactDetailsResponse extends CRMAdapterResponse {

    private CRMContactDetails data;
}
