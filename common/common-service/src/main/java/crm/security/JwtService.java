package crm.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import crm.exception.MicroserviceException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Date;
import java.util.Optional;

@Slf4j
@Service
public class JwtService {

    private static long EXPIRATION_TIME = 1000 * 60 * 60 * 24 * 10; // 10 days

    private static String SECRET = "0Il7o4eUuoBdOqk3+Y4xSq5E2lGJFallOcdKRuzR7X/tpvqgJ9ka7QHJi1BreAN1wDgyz9AMV562ipLrpqQVfHzo8B9ce8A6gSjs00tGOSzMUrSuGWzCiAKkqsb3rnWBPEoVTg==";

    public Token createToken(String externalId, String keycloakExternalId) {
        long exp = System.currentTimeMillis() + EXPIRATION_TIME;
        var accessToken = Jwts.builder()
                .setSubject(externalId)
                .claim("keycloakExternalId", keycloakExternalId)
                .setExpiration(new Date(exp))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
        return Token.builder().accessToken(accessToken).build();
    }

    public DecodedToken parseToken(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        return parseToken(token);
    }

    public Authentication parseTokenToAuthentication(HttpServletRequest request) {
        var token = request.getHeader("Authorization");
        return Optional.ofNullable(parseToken(token)).map(AuthenticationUser::new).orElse(null);
    }

    public DecodedToken parseToken(String token) {
        if (token == null || !token.startsWith("Bearer "))
            return null;

        var trimmedToken = token.substring(7);
        var claims = Jwts.parser()
                .setSigningKey(SECRET)
                .parseClaimsJws(trimmedToken)
                .getBody();
        return new DecodedToken(claims.get("keycloakExternalId", String.class), claims.getSubject());
    }

    public DecodedToken decodeKeycloakToken(String JWTEncoded) {
        var split = JWTEncoded.split("\\.");
        try {
            return new ObjectMapper().readValue(getJson(split[1]), DecodedToken.class);
        } catch (IOException e) {
            log.info("Error on decoding keycloak jwt", e);
            throw new MicroserviceException(HttpStatus.UNAUTHORIZED, e.getMessage());
        }
    }

    private static String getJson(String strEncoded) {
        byte[] decodedBytes = Base64.getDecoder().decode(strEncoded);
        return new String(decodedBytes, StandardCharsets.UTF_8);
    }
}
