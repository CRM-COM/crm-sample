package crm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableConfigurationProperties
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class SwaggerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerGatewayApplication.class, args);
    }
}
