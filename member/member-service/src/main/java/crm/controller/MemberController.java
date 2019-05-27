package crm.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.*;

import crm.model.MemberDto;
import crm.model.MemberOrganisationCreateDto;
import crm.model.MemberOrganisationCreateResponse;
import crm.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import crm.model.MemberCreateDto;
import crm.model.MemberResponseDto;
import crm.service.MemberOutService;

@RestController
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberOutService memberOutService;
    private final MemberReadService memberReadService;

    @PostMapping
    public MemberResponseDto createMember(@RequestBody MemberCreateDto member) {
        return memberOutService.addMember(member);
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

    @GetMapping("/test")
    public String test() {
        return "test";
    }

}