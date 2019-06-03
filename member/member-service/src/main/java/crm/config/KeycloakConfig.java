package crm.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Setter
@RefreshScope
public class KeycloakConfig {

    private String clientId;

    private String clientSecret;

}