package crm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import crm.model.MemberOrganisationCreateDto;
import crm.model.MemberOrganisationCreateResponse;
import lombok.RequiredArgsConstructor;
import crm.model.MemberCreateDto;
import crm.model.MemberResponseDto;
import crm.service.MemberOutService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberOutService memberOutService;

    @PostMapping
    public MemberResponseDto createMember(@RequestBody MemberCreateDto member) {
        return memberOutService.addMember(member);
    }

    @PostMapping("/member/{externalId}/organisation")
    public MemberOrganisationCreateResponse createOrganisation(
        @PathVariable("externalId") String externalId,
        @RequestBody MemberOrganisationCreateDto organisationCreateDto) {
        return memberOutService.addOrganisation(externalId, organisationCreateDto);
    }
    @GetMapping("/test")
    public String test() {
        return "test";
    }

}