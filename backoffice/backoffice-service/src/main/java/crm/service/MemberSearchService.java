package crm.service;

import crm.model.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberSearchService {

    private final MemberProxyService memberProxyService;

    public Page<MemberDto> search(String criteria, String query) {
        return memberProxyService.search(criteria, query);
    }
}
