package crm.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class MicroserviceException extends RuntimeException {

  private HttpStatus errorCode;

  private String errorDescription;

  public MicroserviceException(HttpStatus errorCode, String errorDescription) {
    super(errorDescription);

    this.errorCode = errorCode;
    this.errorDescription = errorDescription;
  }

  public MicroserviceException(String errorDescription) {
    super(errorDescription);

    this.errorCode = HttpStatus.BAD_REQUEST;
    this.errorDescription = errorDescription;
  }
}
