package crm.service;

import crm.model.MemberSearchDto;
import crm.model.RestPageImpl;
import lombok.RequiredArgsConstructor;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import static org.jboss.resteasy.core.MessageBodyParameterInjector.getBody;

@Service
@RequiredArgsConstructor
class MemberProxyService {

  private final RestTemplate restTemplate;

  Page<MemberSearchDto> search(String criteria, String query) {
    return restTemplate.exchange(
        "http://member-service:9014/internal/member/search?criteria=" + criteria + "&query=" + query,
        HttpMethod.GET, null, new ParameterizedTypeReference<RestPageImpl<MemberSearchDto>>() { }).getBody();
  }
}
