package crm.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Component
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CRMConfig {

    @Value("${crm.url}")
    private String url;

    @Value("${crm.refreshTokenActive:true}")
    private boolean refreshTokenActive;

    @Value("${crm.username}")
    private String username;

    @Value("${crm.password}")
    private String password;

    @Value("${crm.organisation}")
    private String organisation;
    
    @Value("${crm.tokens.count:64}")
    private int tokensCount;
}
