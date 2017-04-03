package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.security.oauth2.client.DefaultOAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2ClientContext;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.AccessTokenProvider;
import org.springframework.security.oauth2.client.token.AccessTokenProviderChain;
import org.springframework.security.oauth2.client.token.AccessTokenRequest;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.client.token.DefaultAccessTokenRequest;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeAccessTokenProvider;
import org.springframework.security.oauth2.client.token.grant.code.AuthorizationCodeResourceDetails;
import org.springframework.security.oauth2.common.AuthenticationScheme;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Arrays;

@Configuration
@EnableOAuth2Client
public class ConfiguracaoResource {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private BookserverClientTokenServices clientTokenServices;

    @Bean
    public OAuth2ProtectedResourceDetails bookserver() {
        AuthorizationCodeResourceDetails detailsForBookserver = new AuthorizationCodeResourceDetails();

        detailsForBookserver.setId("bookserver");
        detailsForBookserver.setTokenName("oauth_token");

        // credenciais do client
        detailsForBookserver.setClientId("cliente-app");
        detailsForBookserver.setClientSecret("123456");

        // endpoint de solicitacao do token
        detailsForBookserver.setAccessTokenUri("http://localhost:8080/oauth/token");

        // endpoint de autorizacao
        detailsForBookserver.setUserAuthorizationUri("http://localhost:8080/oauth/authorize");

        // escopos
        detailsForBookserver.setScope(Arrays.asList("read", "write"));

        // url de redirecionamento
        detailsForBookserver.setPreEstablishedRedirectUri(("http://localhost:9000/integracao/callback"));
        detailsForBookserver.setUseCurrentUri(false);

        detailsForBookserver.setClientAuthenticationScheme(AuthenticationScheme.header);
        return detailsForBookserver;
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    @Autowired
    private AccessTokenRequest accessTokenRequest;

    @Bean
    @Scope(value = "session", proxyMode = ScopedProxyMode.INTERFACES)
    public OAuth2RestOperations restTemplate() {
        OAuth2RestTemplate template = new OAuth2RestTemplate(bookserver(),
                new DefaultOAuth2ClientContext(accessTokenRequest));

        AccessTokenProviderChain provider = new AccessTokenProviderChain(Arrays.asList(new AuthorizationCodeAccessTokenProvider()));
        provider.setClientTokenServices(clientTokenServices);
        return template;
    }

//    @Bean
//    public OAuth2RestTemplate bookserverRestTemplate(OAuth2ClientContext clientContext) {
//
//        OAuth2RestTemplate template = new OAuth2RestTemplate(bookserver(), clientContext);
//
//        AccessTokenProviderChain accessTokenProvider = new AccessTokenProviderChain(
//                Arrays.<AccessTokenProvider> asList(new AuthorizationCodeAccessTokenProvider())
//        );
//
//        accessTokenProvider.setClientTokenServices(clientTokenServices);
//
//        template.setAccessTokenProvider(accessTokenProvider);
//
//        return template;
//    }
}
