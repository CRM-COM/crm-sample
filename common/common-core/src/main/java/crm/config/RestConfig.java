package crm.config;

import java.util.Arrays;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestConfig {

	@Value("${rest.connectTimeout:15000}")
	int connectTimeout;

	@Value("${rest.readTimeout:30000}")
	int readTimeout;

    @Value("${rest.maxConnTotal:64}")
    int maxConnTotal;

    @Value("${rest.maxConnPerRoute:64}")
    int maxConnPerRoute;

    private final RestTemplateResponseErrorHandler restTemplateResponseErrorHandler;

    @Autowired
    public RestConfig(RestTemplateResponseErrorHandler restTemplateResponseErrorHandler) {
        this.restTemplateResponseErrorHandler = restTemplateResponseErrorHandler;
    }

    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder, HttpClient httpClient) {
        RestTemplate ret= builder
                .errorHandler(restTemplateResponseErrorHandler)
                .setConnectTimeout(connectTimeout)
                .setReadTimeout(readTimeout)
                .build();
        ret.getMessageConverters().add(jacksonByDefault());
        HttpComponentsClientHttpRequestFactory httpRequestFactory = new
                HttpComponentsClientHttpRequestFactory();
        httpRequestFactory.setReadTimeout(readTimeout);
        httpRequestFactory.setConnectTimeout(connectTimeout);
        httpRequestFactory.setHttpClient(httpClient);
        ret.setRequestFactory(httpRequestFactory);
        return ret;
    }

    // handle badly behaved APIs that use JSON but don't set Content-Type (for example Apple)
    private HttpMessageConverter jacksonByDefault() {
        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        converter.setSupportedMediaTypes(Arrays.asList(MediaType.parseMediaType("text/plain;charset=utf-8"), MediaType.APPLICATION_OCTET_STREAM));
        return converter;
    }

    @Bean
    public PoolingHttpClientConnectionManager poolingHttpClientConnectionManager() {
        PoolingHttpClientConnectionManager result = new PoolingHttpClientConnectionManager();
        result.setDefaultMaxPerRoute(maxConnPerRoute);
        result.setMaxTotal(maxConnTotal);
        return result;
    }

    @Bean
    public CloseableHttpClient httpClient(PoolingHttpClientConnectionManager poolingHttpClientConnectionManager) {
        CloseableHttpClient result = HttpClientBuilder
                .create()
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
        return result;
    }

    @Bean
    public Jackson2ObjectMapperBuilder objectMapperBuilder() {

        return new Jackson2ObjectMapperBuilder() {

            @Override
            public void configure(ObjectMapper objectMapper) {
                super.configure(objectMapper);
                objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.NONE);
                objectMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
            }
        };
    }

}
