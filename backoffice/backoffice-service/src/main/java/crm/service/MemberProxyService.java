package crm.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
class MemberProxyService {

    private final RestTemplate restTemplate;

    String search(String criteria, String query, String page, String size, String sort) {
        return restTemplate.exchange(
                "http://member-service:9014/internal/member/search?" +
                        "criteria=" + criteria +
                        "&query=" + query +
                        "&page=" + page +
                        "&size=" + size +
                        "&sort=" + sort,
                HttpMethod.GET, null, String.class).getBody();
    }
}
