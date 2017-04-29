package br.com.casadocodigo.configuracao.seguranca.openid;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class DiscoveryDocument {

    @Getter
    @Value("${google.client_id}")
    private String clientId;

    @Getter
    @Value("${google.client_secret}")
    private String clientSecret;

    @Getter
    @Value("${google.access_token_uri}")
    private String accessTokenUri;

    @Getter
    @Value("${google.user_authorization_uri}")
    private String userAuthorizationUri;

    @Getter
    @Value("${google.redirect_uri}")
    private String redirectUri;

    @Getter
    @Value("${google.userinfo_endpoint}")
    private String userInfoEndpoint;

}
