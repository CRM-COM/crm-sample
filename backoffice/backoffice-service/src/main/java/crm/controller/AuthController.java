package crm.controller;

import crm.model.AuthDto;
import crm.security.Token;
import crm.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
