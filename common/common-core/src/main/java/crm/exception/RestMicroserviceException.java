package crm.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class RestMicroserviceException {

    private HttpStatus errorCode;

    private String errorDescription;

    public RestMicroserviceException(HttpStatus errorCode, String errorDescription) {
        this.errorCode = errorCode;
        this.errorDescription = errorDescription;
    }

    public RestMicroserviceException(MicroserviceException ex) {
        this.errorCode = ex.getErrorCode();
        this.errorDescription = ex.getErrorDescription();
    }
}