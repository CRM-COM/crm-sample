package crm.controller;

import crm.model.CRMContactDetails;
import crm.service.MemberBackofficeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/backoffice")
@RequiredArgsConstructor
public class MemberBackofficeController {

    private final MemberBackofficeService service;

    @GetMapping("/member/search")
    public ResponseEntity<String> search(@RequestParam(name = "criteria", required = false, defaultValue = "") String criteria,
                                        @RequestParam(name = "query", required = false, defaultValue = "") String query,
                                        @RequestParam(name = "page", required = false, defaultValue = "") String page,
                                        @RequestParam(name = "size", required = false, defaultValue = "") String size,
                                        @RequestParam(name = "sort", required = false, defaultValue = "") String sort) {
        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.search(criteria, query, page, size, sort));
    }

    @GetMapping("/member/{externalId}")
    public CRMContactDetails getCrmMember(@PathVariable String externalId) {
        return service.getCrmMember(externalId);
    }
}
