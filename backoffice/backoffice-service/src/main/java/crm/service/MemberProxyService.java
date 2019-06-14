package crm.service;

import crm.model.MemberDto;
import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
class MemberProxyService {

    private final RestTemplate restTemplate;

    Page<MemberDto> search(String criteria, String query) {
        return restTemplate.exchange("http://member-service/internal/member/search?criteria=" + criteria + "&query=" + query, HttpMethod.GET, null, new ParameterizedTypeReference<Page<MemberDto>>() {
        }).getBody();
    }
}
