package crm.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Configuration;

import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "keycloak")
@Setter
@Getter
@RefreshScope
public class KeycloakConfig {

    private String clientId;

    private String clientSecret;

    private String realm;

    private String username;

    private String password;

}