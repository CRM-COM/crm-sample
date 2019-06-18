package crm.controller;

import crm.model.AuthDto;
import crm.security.Token;
import crm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/backoffice")
public class AuthController {

    private final AuthService authService;

    @PostMapping("/authenticate")
    public Token authenticate(@RequestBody @Valid AuthDto auth) {
        return authService.authenticate(auth);
    }

    @GetMapping("/token/valid")
    public void validateToken(@RequestHeader("Authorization") String token) {
        authService.validateToken(token);
    }
}
