package crm.config.security;

import crm.security.WebSecurityConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigure {

    @Override
    protected String[] permitAllPost() {
        return new String[]{
                "/actuator/**",
                "/backoffice/organisation/register",
                "/backoffice/authenticate",
        };
    }

    @Override
    protected String[] permitAllGet() {
        return new String[]{
                "/actuator/**",

                "/index.html",
                "/assets/**",
                "/**.js",
                "/**.css",
                "/**.js.map",
                "/swagger-resources/**",
                "/swagger-ui.html",
                "/v2/api-docs",
                "/webjars/**"
        };
    }
}
