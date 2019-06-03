package crm.config;

import crm.exception.MicroserviceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResponseErrorHandler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import static org.springframework.http.HttpStatus.Series.CLIENT_ERROR;

@Component
public class RestTemplateResponseErrorHandler implements ResponseErrorHandler {

    @Override
    public boolean hasError(ClientHttpResponse httpResponse) throws IOException {

        return httpResponse.getStatusCode().series() == CLIENT_ERROR;
    }

    @Override
    public void handleError(ClientHttpResponse httpResponse) throws IOException {
        var response = new BufferedReader(new InputStreamReader(httpResponse.getBody())).lines().reduce(String::concat).orElse(null);

        if (httpResponse.getStatusCode().series() == HttpStatus.Series.CLIENT_ERROR) {
            throw new MicroserviceException(httpResponse.getStatusCode(), httpResponse.getStatusText(), response);
        }
    }
}