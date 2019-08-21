package crm.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Slf4j
@RestController("/refresh")
public class SpectacleController {

    @GetMapping
    public void refresh() throws IOException, InterruptedException {
        log.info("Creating spectacle");
        String commercePath = "http://commerce-service:9017/v2/api-docs";
        String swaggerJson = new RestTemplate().getForObject(commercePath, String.class);
        if (swaggerJson == null) return;
        Files.write(Paths.get("./swagger.json"), swaggerJson.getBytes());
        Runtime.getRuntime().exec(new String[]{"bash", "-c", "spectacle swagger.json -t ./src/main/resources/public -f commerce.html"}).waitFor();
        log.info("Created spectacle");
    }
}
