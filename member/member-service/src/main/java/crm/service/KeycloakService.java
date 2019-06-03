package crm.service;

import crm.exception.MicroserviceException;
import crm.model.AuthenticationDto;
import crm.model.MemberCreateDto;
import crm.security.CrmKeycloakToken;
import lombok.RequiredArgsConstructor;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import javax.ws.rs.core.Response;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KeycloakService {

    private static final String GET_TOKEN_URL = "https://keycloak.crmcloudapi.com/auth/realms/crm-dev/protocol/openid-connect/token";

    private final RestTemplate restTemplate;

    public void createKeycloakUser(MemberCreateDto member) {
        var user = createUser(member);
        String realm = "crm-dev";
        var keycloak = createKeycloak(realm);
        var realmResource = keycloak.realm(realm);
        var userResource = realmResource.users();
        var response = userResource.create(user);
        getCheckResponse(response);
    }

    private void getCheckResponse(Response response) {
        if(response.getStatus() != 200)
            throw new MicroserviceException(HttpStatus.valueOf(response.getStatus()), null);
    }

    private Keycloak createKeycloak(String realm) {
        return KeycloakBuilder.builder()
                .serverUrl("https://keycloak.crmcloudapi.com/auth")
                .realm(realm)
                .grantType(OAuth2Constants.PASSWORD)
                .clientId("crm-dev")
                .clientSecret("08e7e73a-6fb2-41ff-88c9-6178300f7b2a")
                .username("admin")
                .password("password123")
                .build();
    }

    private String getUserId(Response response) {
        return response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");
    }

    private UserRepresentation createUser(MemberCreateDto member) {
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

        return user;
    }

    private CredentialRepresentation createCrdential(MemberCreateDto member) {
        var credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(member.getPassword());
        return credential;
    }


    public CrmKeycloakToken auth(AuthenticationDto authDto) {
        String plainCreds = "crm-dev:08e7e73a-6fb2-41ff-88c9-6178300f7b2a";
        var basicAuth = Base64.getEncoder().encodeToString(plainCreds.getBytes());
        var headers = new HttpHeaders();
        headers.add("Authorization", "Basic " + basicAuth);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        var map = new LinkedMultiValueMap<String, String>();
        map.add("username", authDto.getEmail());
        map.add("password", authDto.getPassword());
        map.add("grant_type", "password");

        var request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        return restTemplate.postForObject(GET_TOKEN_URL, request, CrmKeycloakToken.class);
    }
}
