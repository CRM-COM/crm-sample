package crm.config;

import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import lombok.Setter;

@Configuration
@ConfigurationProperties(prefix = "encryption")
@Setter
@RefreshScope
public class EncryptionConfig {

    private String seed;

    private int strength;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength, new SecureRandom(seed.getBytes(StandardCharsets.UTF_8)));
    }
}