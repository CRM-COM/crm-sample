package crm.config.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@Service
public class JwtService {

    private static long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10 days
    private static String SECRET = "ThisIsASecret";

    public String createToken(String externalId) {
        return Jwts.builder()
                .setSubject(externalId)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    public String parseToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        return parseToken(token);
    }

    public Authentication parseTokenToAuthentication(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        return Optional.ofNullable(parseToken(token)).map(AuthenticationUser::new).orElse(null);
    }

    private String parseToken(String token) {
        if (token == null || !token.startsWith("Bearer "))
            return null;

        var trimmedToken = token.substring(7);
        return Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(trimmedToken)
                .getBody()
                .getSubject();
    }
}
