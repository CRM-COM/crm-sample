package crm.controller;

import crm.service.MemberSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<String> search(@RequestParam(name = "criteria", required = false) String criteria,
                                        @RequestParam(name = "query", required = false) String query,
                                        @RequestParam(name = "page", required = false, defaultValue = "") String page,
                                        @RequestParam(name = "size", required = false, defaultValue = "") String size,
                                        @RequestParam(name = "sort", required = false, defaultValue = "") String sort) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.search(criteria, query, page, size, sort));
    }
}
