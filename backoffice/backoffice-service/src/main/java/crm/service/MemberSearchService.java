package crm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberProxyService memberProxyService;

    public String search(String criteria, String query, String page, String size, String sort) {
        return memberProxyService.search(criteria, query, page, size, sort);
    }
}
