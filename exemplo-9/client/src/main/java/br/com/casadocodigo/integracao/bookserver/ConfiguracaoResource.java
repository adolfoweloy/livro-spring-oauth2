package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ConfiguracaoResource {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Bean
    public ClientTokenServices clientTokenServices() {
        return new BookserverClientTokenServices(usuariosRepository);
    }

    @Bean
    public OAuth2ProtectedResourceDetails bookserver() {
        AuthorizationCodeResourceDetails detailsForBookserver = new AuthorizationCodeResourceDetails();

        detailsForBookserver.setId("bookserver");
        detailsForBookserver.setTokenName("oauth_token");
        detailsForBookserver.setClientId("cliente-app");
        detailsForBookserver.setClientSecret("123456");
        detailsForBookserver.setAccessTokenUri("http://localhost:8080/oauth/token");
        detailsForBookserver.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");
        detailsForBookserver.setScope(Arrays.asList("read", "write"));

        detailsForBookserver.setPreEstablishedRedirectUri(("http://localhost:9000/integracao/callback"));
        detailsForBookserver.setUseCurrentUri(false);

        detailsForBookserver.setClientAuthenticationScheme(AuthenticationScheme.header);
        return detailsForBookserver;
    }

    @Autowired
    @Qualifier("accessTokenRequest")
    private AccessTokenRequest accessTokenRequest;

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations oauth2RestTemplate() {

        OAuth2ClientContext context = new DefaultOAuth2ClientContext(accessTokenRequest);
        OAuth2ProtectedResourceDetails resourceDetails = bookserver();

        OAuth2RestTemplate template = new OAuth2RestTemplate(resourceDetails, context);

        AccessTokenProviderChain provider = new AccessTokenProviderChain(
            Arrays.asList(new AuthorizationCodeAccessTokenProvider()));

        provider.setClientTokenServices(clientTokenServices());
        template.setAccessTokenProvider(provider);

        return template;
    }

}
