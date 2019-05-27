package crm.controller;

import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/test")
    public String test() {
        return "test";
    }

    //POST /member with Body
//    {name, email, password etc}
//
//    Creates GUID sends back.
//
//    Event to DBMS, then to Keycloak

}