package crm.controller;

import crm.model.*;
import crm.security.Token;
import crm.service.MemberOutService;
import crm.service.MemberReadService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberOutService memberOutService;
    private final MemberReadService memberReadService;

    @PostMapping
    public Token createMember(@Valid @RequestBody MemberCreateDto member) {
        return memberOutService.addMember(member);
    }

    @PostMapping("/{externalId}/organisation")
    public MemberOrganisationCreateResponse createOrganisation(
        @PathVariable("externalId") String externalId,
        @RequestBody MemberOrganisationCreateDto organisationCreateDto) {
        return memberOutService.addOrganisation(externalId, organisationCreateDto);
    }

    @GetMapping("/search")
    public Page<MemberDto> search(@RequestParam(name = "criteria", required = false) String criteria,
        @RequestParam(name = "query", required = false) String query, Pageable pageable) {
        return memberReadService.search(criteria, query, pageable);
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
    public Token authenticate(@Valid @RequestBody AuthenticationDto loginDto) {
        return memberReadService.authenticate(loginDto);
    }

}