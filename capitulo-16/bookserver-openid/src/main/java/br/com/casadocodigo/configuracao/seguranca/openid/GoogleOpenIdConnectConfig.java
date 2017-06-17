package br.com.casadocodigo.configuracao.seguranca.openid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import static java.util.Arrays.asList;

@Configuration
@EnableOAuth2Client
public class GoogleOpenIdConnectConfig {

    @Autowired
    private DiscoveryDocument discoveryDocument;

    @Autowired
    private OpenIdTokenServices clientTokenServices;

    @Bean
    public OAuth2ProtectedResourceDetails protectedResourceDetails() {
        AuthorizationCodeResourceDetails details = new AuthorizationCodeResourceDetails();

        details.setClientId(discoveryDocument.getClientId());
        details.setClientSecret(discoveryDocument.getClientSecret());
        details.setAccessTokenUri(discoveryDocument.getAccessTokenUri());
        details.setUserAuthorizationUri(discoveryDocument.getUserAuthorizationUri());
        details.setPreEstablishedRedirectUri(discoveryDocument.getRedirectUri());
        details.setScope(asList("openid", "email", "profile"));

        details.setUseCurrentUri(false);
        return details;
    }

    @Bean
    public OAuth2RestTemplate googleOpenIdRestTemplate(OAuth2ClientContext clientContext) {
        OAuth2RestTemplate template = new OAuth2RestTemplate(
            protectedResourceDetails(), clientContext);

        template.setAccessTokenProvider(getAccessTokenProviderWithClientTokenServices());

        return template;
    }

    private AccessTokenProviderChain getAccessTokenProviderWithClientTokenServices() {
        AccessTokenProviderChain provider = new AccessTokenProviderChain(
                asList(new AuthorizationCodeAccessTokenProvider()));
        provider.setClientTokenServices(clientTokenServices);
        return provider;
    }

}
