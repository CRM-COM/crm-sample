package crm.config.security;

import crm.security.WebSecurityConfigure;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

//@Configuration
//@EnableWebSecurity
public class SecurityConfig  {


    protected String[] permitAll() {
        return new String[]{
                "/actuator/**",
                "/member/authenticate",
                "/member"
        };
    }
}
