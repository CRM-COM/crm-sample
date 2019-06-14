package crm.controller;

import crm.model.MemberDto;
import crm.service.MemberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
public class MemberSearchController {

    private final MemberSearchService service;

    @GetMapping("/member/search")
    public Page<MemberDto> search(@RequestParam(name = "criteria", required = false) String criteria,
                                  @RequestParam(name = "query", required = false) String query) {
        return service.search(criteria, query);
    }
}
