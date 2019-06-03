package crm.service;

import crm.config.KeycloakConfig;
import crm.event.MemberCreateEvent;
import crm.model.AuthenticationDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String GET_TOKEN_URL = "/realms/crm-dev/protocol/openid-connect/token";
    private static final String KEYCLOAK_URL = "https://keycloak.crmcloudapi.com/auth";

    private final RestTemplate restTemplate;
    private final KeycloakConfig config;

    String createKeycloakUser(MemberCreateEvent member) {
        var user = createUser(member);
        var response = createKeycloak().realm(config.getRealm()).users().create(user);
        checkResponse(response);
        return getUserId(response);
    }

    private void checkResponse(Response response) {
        if (response.getStatus() != 201)
            log.info("Keycloak create member error {}", response.getStatus());
    }

    private Keycloak createKeycloak() {
        return KeycloakBuilder.builder()
                .serverUrl(KEYCLOAK_URL)
                .realm(config.getRealm())
                .grantType(OAuth2Constants.PASSWORD)
                .clientId(config.getClientId())
                .clientSecret(config.getClientSecret())
                .username(config.getUsername())
                .password(config.getPassword())
                .build();
    }

    private String getUserId(Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    private UserRepresentation createUser(MemberCreateEvent member) {
        var user = new UserRepresentation();
        user.setFirstName(member.getForename());
        user.setLastName(member.getSurname());
        user.setUsername(member.getNickname());
        user.setEmail(member.getEmail());
        var attributes = new HashMap<String, List<String>>();
        attributes.put("phoneNumber", Collections.singletonList(member.getPhoneNumber()));
        user.setAttributes(attributes);
        user.setCredentials(Collections.singletonList(createCrdential(member)));
        user.setId(UUID.randomUUID().toString());
        user.setEnabled(true);

        return user;
    }

    private CredentialRepresentation createCrdential(MemberCreateEvent member) {
        var credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(member.getPassword());
        return credential;
    }

    public CrmKeycloakToken auth(AuthenticationDto authDto) {
        var headers = getHeaders();
        var map = getBody(authDto);
        var request = new HttpEntity<MultiValueMap<String, String>>(map, headers);
        return restTemplate.postForObject(KEYCLOAK_URL + GET_TOKEN_URL, request, CrmKeycloakToken.class);
    }

    private LinkedMultiValueMap<String, String> getBody(AuthenticationDto authDto) {
        var map = new LinkedMultiValueMap<String, String>();
        map.add("username", authDto.getUsername());
        map.add("password", authDto.getPassword());
        map.add("grant_type", "password");
        return map;
    }

    private HttpHeaders getHeaders() {
        var headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + getAuthHeader());
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    private String getAuthHeader() {
        String plainCredentials = config.getClientId() + ":" + config.getClientSecret();
        return Base64.getEncoder().encodeToString(plainCredentials.getBytes());
    }
}
