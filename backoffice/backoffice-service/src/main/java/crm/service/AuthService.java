package crm.service;

import crm.exception.MicroserviceException;
import crm.model.AuthDto;
import crm.repository.UserRepository;
import crm.security.JwtService;
import crm.security.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;

    public Token authenticate(AuthDto auth) {
        final var user = userRepository.findByEmail(auth.getEmail())
                .orElseThrow(() -> new MicroserviceException(HttpStatus.UNAUTHORIZED, "User not found by email"));
        checkPassword(user.getPassword(), auth.getPassword());
        return jwtService.createToken(user.getExternalId());
    }

    private void checkPassword(String userPassword, String authPassword) {
        if (!new BCryptPasswordEncoder().matches(authPassword, userPassword))
            throw new MicroserviceException(HttpStatus.UNAUTHORIZED, "Incorrect password");
    }
}
