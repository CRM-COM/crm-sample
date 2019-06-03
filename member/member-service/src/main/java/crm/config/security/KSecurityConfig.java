package crm.config.security;

import crm.security.KWebSecurityConfigure;
import org.keycloak.adapters.springsecurity.KeycloakSecurityComponents;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@ComponentScan(
        basePackageClasses = KeycloakSecurityComponents.class,
        excludeFilters = @ComponentScan.Filter(type = FilterType.REGEX,
                pattern = "org.keycloak.adapters.springsecurity.management.HttpSessionManager"))
@EnableWebSecurity
public class KSecurityConfig extends KWebSecurityConfigure {

    @Override
    protected String[] permitAllPost() {
        return new String[]{
                "/actuator/**",
                "/member/authenticate",
                "/member"
        };
    }

    @Override
    protected String[] permitAllGet() {
        return new String[]{
                "/actuator/**"
        };
    }
}