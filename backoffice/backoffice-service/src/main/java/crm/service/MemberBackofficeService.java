package crm.service;

import crm.model.CRMContactDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberBackofficeService {

    private final MemberProxyService memberProxyService;

    public String search(String criteria, String query, String page, String size, String sort) {
        return memberProxyService.search(criteria, query, page, size, sort);
    }

    public CRMContactDetails getCrmMember(String memberExternalId) {
        return memberProxyService.getCrmMember(memberExternalId);
    }
}
