package crm.model.crm;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMAuthenticateResponse extends CRMAdapterResponse {
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CRMAuthenticateResponseData {
        @JsonProperty("token")
        private String token;
    }
    
    private CRMAuthenticateResponseData data;    
}
