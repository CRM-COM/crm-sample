package crm.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Service
public class JwtService {

    private static long EXPIRATIONTIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private static String SECRET = "ThisIsASecret";

    public String createToken(String externalId) {
        return Jwts.builder()
                .setSubject(externalId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATIONTIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null) {
            return Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
        }
        return null;
    }

    public Authentication parseTokenToAuthentication(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        if (token != null) {
            var externalId = Jwts.parser()
                    .setSigningKey(SECRET)
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
            return new AuthenticationUser(externalId);
        }
        return null;
    }
}
