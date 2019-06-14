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
        };
    }

    @Override
    protected String[] permitAllGet() {
        return new String[]{
                "/actuator/**"
        };
    }
}
