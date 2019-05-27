package crm.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTLoginFilter extends AbstractAuthenticationProcessingFilter {

    private JwtService jwtService;

    public JWTLoginFilter(String url, AuthenticationManager authenticationManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authenticationManager);

        jwtService = new JwtService();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws AuthenticationException, IOException {
        var credentials = new ObjectMapper().readValue(httpServletRequest.getInputStream(), AccountCredentials.class);
        var token = new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword());
        return getAuthenticationManager().authenticate(token);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException {
        var externalId = authentication.getName();
        jwtService.createToken(response, externalId);
    }
}
