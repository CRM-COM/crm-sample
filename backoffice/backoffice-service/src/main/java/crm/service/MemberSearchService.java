package crm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberProxyService memberProxyService;

    public String search(String criteria, String query) {
        return memberProxyService.search(criteria, query);
    }
}
