package crm.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import crm.model.MemberCreateDto;
import crm.model.MemberResponseDto;
import crm.service.MemberOutService;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class MemberController {

    private final MemberOutService memberOutService;

    @PostMapping("/member")
    public MemberResponseDto createMember(@RequestBody MemberCreateDto member) {
        return memberOutService.addMember(member);
    }

    //POST /member with Body
//    {name, email, password etc}
//
//    Creates GUID sends back.
//
//    Event to DBMS, then to Keycloak

}