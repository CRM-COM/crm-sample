package crm;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;


@Slf4j
@EnableConfigurationProperties
@SpringBootApplication
@EnableDiscoveryClient
@EnableZuulProxy
public class SwaggerGatewayApplication {
    public static void main(String[] args) {
        SpringApplication.run(SwaggerGatewayApplication.class, args);
    }


    @PostConstruct
    public void spectacle() throws IOException, InterruptedException {
        log.info("Creating spectacle");
        String commercePath = "http://commerce-service:9017/v2/api-docs";
        String swaggerJson = new RestTemplate().getForObject(commercePath, String.class);
        if(swaggerJson == null) return;
        Files.write(Paths.get("./swagger.json"), swaggerJson.getBytes());
        Runtime.getRuntime().exec(new String[]{"bash","-c","spectacle swagger.json -t ./src/main/resources/public"}).waitFor();
        log.info("Created spectacle");
    }
}
