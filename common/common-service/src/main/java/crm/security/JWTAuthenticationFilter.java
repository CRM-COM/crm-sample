package crm.security;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAuthenticationFilter extends GenericFilterBean {

    private JwtService jwtService;

    JWTAuthenticationFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain filterChain) throws IOException, ServletException {
        try {
            var authentication = jwtService.parseTokenToAuthentication((HttpServletRequest) request);

            SecurityContextHolder.getContext().setAuthentication(authentication);
            filterChain.doFilter(request, response);
        } catch (MalformedJwtException e) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(403);
            res.getWriter().write("Invalid jwt token");
        } catch (Exception e) {
            HttpServletResponse res = (HttpServletResponse) response;
            res.setStatus(401);
            res.getWriter().write(e.getMessage());
        }
    }
}