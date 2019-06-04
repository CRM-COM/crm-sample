package crm.model.crm;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CRMAdapterResponse {
    public static final String VALID_RESPONSE_CODE = "OK";
    
    @Getter
    @Setter
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CRMAdapterResponseStatus {
        private String code;

        private String description;
        
        private String message;
    }
    
    private CRMAdapterResponseStatus status;
    
    public boolean isValid() {
        return VALID_RESPONSE_CODE.equals(getStatus().getCode());
    }
}
