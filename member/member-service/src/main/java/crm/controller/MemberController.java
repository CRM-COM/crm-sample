package crm.controller;

import crm.model.*;
import crm.security.CrmKeycloakToken;
import crm.security.Token;
import crm.service.KeycloakService;
import crm.service.MemberOutService;
import crm.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberOutService memberOutService;
    private final MemberReadService memberReadService;
    private final KeycloakService keycloakService;

    @PostMapping
    public Token createMember(@RequestBody MemberCreateDto member) {
        return memberOutService.addMember(member);
    }

    @PostMapping("/k")
    public void createMemberK(@RequestBody MemberCreateDto member) {
        keycloakService.createKeycloakUser(member);
    }

    @PostMapping("/{externalId}/organisation")
    public MemberOrganisationCreateResponse createOrganisation(
        @PathVariable("externalId") String externalId,
        @RequestBody MemberOrganisationCreateDto organisationCreateDto) {
        return memberOutService.addOrganisation(externalId, organisationCreateDto);
    }

    @GetMapping("/code/{idOrCard}")
    public MemberDto getMemberByIdOrCard(@PathVariable("idOrCard") String idOrCard) {
      return memberReadService.getMemberByIdOrCard(idOrCard);
    }

    @GetMapping
    public MemberDto getMember(@RequestHeader("Authorization") String token) {
        return memberReadService.getMember(token);
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    @PostMapping("/authenticate")
    public Token authenticate(@RequestBody AuthenticationDto loginDto) {
        return memberReadService.authenticate(loginDto);
    }

    @PostMapping("/auth")
    public CrmKeycloakToken auth(@RequestBody AuthenticationDto loginDto) {
        return keycloakService.auth(loginDto);
    }

}