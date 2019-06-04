package crm.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class CRMAuthenticateRequest extends CRMAdapterRequest {
    private String username;

    private String password;

    private String organisation;
}
