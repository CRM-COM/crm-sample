package crm.controller;

import crm.model.MemberDto;
import crm.service.MemberReadService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal")
@RequiredArgsConstructor
public class InternalMemberController {

    private final MemberReadService service;

    @GetMapping("/member/search")
    public Page<MemberDto> search(@RequestParam(name = "criteria", required = false, defaultValue = "") String criteria,
                                  @RequestParam(name = "query", required = false, defaultValue = "") String query,
                                  Pageable pageable) {
        return service.search(criteria, query, pageable);
    }
}
