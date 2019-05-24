package crm.config;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

import java.io.IOException;

import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import crm.exception.MicroserviceException;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return httpResponse.getStatusCode().series() == CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            throw new MicroserviceException(httpResponse.getStatusCode(), httpResponse.getStatusText());
        }
    }
}