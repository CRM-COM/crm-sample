package crm.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfigure  extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeRequests().antMatchers(permitAll()).permitAll()
                .anyRequest().authenticated()
                .and()
                .addFilterBefore(new JWTLoginFilter("/login", authenticationManager()), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(permitAll());
    }

    protected String[] permitAll() {
        return new String[0];
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception{
        // Create a default account
        auth.inMemoryAuthentication()
                .withUser("admin")
                .password("password")
                .roles("ADMIN");
    }
}
