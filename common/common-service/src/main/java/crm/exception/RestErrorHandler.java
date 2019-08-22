package crm.exception;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.AbstractHandlerExceptionResolver;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class RestErrorHandler {

    @ExceptionHandler(MicroserviceException.class)
    @ResponseBody
    public ResponseEntity<RestMicroserviceException> handleMicroserviceException(MicroserviceException ex) {
        log.error("Error caught: ", ex);

        return new ResponseEntity<>(new RestMicroserviceException(ex), ex.getErrorCode());
    }

    @ExceptionHandler(MalformedJwtException.class)
    @ResponseBody
    public RestMicroserviceException handleMalformedJwtException(MalformedJwtException ex) {
        log.error("Error caught: ", ex);
        return new RestMicroserviceException(HttpStatus.FORBIDDEN, ex.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestMicroserviceException handleIllegalArgumentException(IllegalArgumentException ex) {
        log.error("Error caught: ", ex);

        return new RestMicroserviceException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public RestMicroserviceException handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
        log.error("Error caught: ", ex);

        return new RestMicroserviceException(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    @ExceptionHandler(FileNotFoundException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestMicroserviceException handleFileNotFoundException(FileNotFoundException ex) {
        log.error("Error caught: ", ex);

        return new RestMicroserviceException(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    @ExceptionHandler(ExpiredJwtException.class)
    @ResponseStatus(code = HttpStatus.NOT_FOUND)
    @ResponseBody
    public RestMicroserviceException handleExpiredJwtException(ExpiredJwtException ex) {
        log.error("Error caught: ", ex);

        return new RestMicroserviceException(HttpStatus.UNAUTHORIZED, ex.getMessage());
    }

    /**
     * This class was defined instead of @ExceptionHandler(Exception.class)
     * because we are still in development phase where not all exception types are covered explicitly.
     * We want to use spring default behavior for all not yet recognized exceptions.
     */
    @Component
    @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
    public static class CustomExceptionHandlerExceptionResolver extends AbstractHandlerExceptionResolver {

        @Override
        protected ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response,
                                                  Object handler, Exception ex) {
            try {

                log.error("Unrecoverable exception", ex);
                response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Server error occurred.");
                return new ModelAndView();

            } catch (IOException ioException) {
                logger.warn("Handling of [" + ex.getClass().getName() + "] resulted in exception", ioException);
                return null;
            }
        }
    }
}