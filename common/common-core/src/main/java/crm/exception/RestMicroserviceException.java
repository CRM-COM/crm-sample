package crm.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
public class RestMicroserviceException {

    private HttpStatus errorCode;

    private String errorDescription;

    private String response;

    public RestMicroserviceException(HttpStatus errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public RestMicroserviceException(MicroserviceException ex) {
        this.errorCode = ex.getErrorCode();
        this.errorDescription = ex.getErrorDescription();
        this.response = ex.getResponse();
    }
}