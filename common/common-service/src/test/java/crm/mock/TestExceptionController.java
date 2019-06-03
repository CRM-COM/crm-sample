package crm.mock;

import java.io.FileNotFoundException;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import crm.exception.MicroserviceException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.Data;

@RestController
public class TestExceptionController {

    @GetMapping(value = "/microservice")
    public String handleMicroserviceException() {
        throw new MicroserviceException(HttpStatus.NOT_FOUND, "Enum not found by externalId id");
    }

    @GetMapping(value = "/illegal")
    public String handleIllegalArgumentException() {
        throw new IllegalArgumentException("illegal");
    }

    @GetMapping(value = "/runtime")
    public String handleRuntimeException() {
        throw new RuntimeException("Runtime exception");
    }

    @GetMapping(value = "/fileNotFound")
    public String handleFileNotFoundException() throws FileNotFoundException {
        throw new FileNotFoundException("File not found exception");
    }

    /**
     * The purpose of this method is to test the @Valid annotation
     */
    @GetMapping(value = "/invalid")
    public void handleInValid(@Valid ParamsDto paramsDto) {
    }

    /**
     * The purpose of this method is to test the @ResponseStatus annotation
     */
    @GetMapping(value = "/custom508")
    public void handleCustom508() {
        throw new Custom508Exception();
    }

    @GetMapping(value = "/exception")
    public void handleException() throws Exception {
        throw new Exception();
    }

    @Data
    public static class ParamsDto {
        @NotNull
        private String param;
    }

    @ResponseStatus(HttpStatus.LOOP_DETECTED)
    public static class Custom508Exception extends RuntimeException {

    }
}
