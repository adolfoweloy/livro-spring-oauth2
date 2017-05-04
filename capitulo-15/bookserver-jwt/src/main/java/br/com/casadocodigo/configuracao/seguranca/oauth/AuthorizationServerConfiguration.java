package br.com.casadocodigo.configuracao.seguranca.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration
    extends AuthorizationServerConfigurerAdapter {

    public static final String RESOURCE_ID = "books";

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private ClientDetailsService clientDetailsService;

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        DefaultOAuth2RequestFactory requestFactory
            = new DefaultOAuth2RequestFactory(clientDetailsService);

        requestFactory.setCheckUserScopes(true);

        endpoints
                .authenticationManager(authenticationManager)
                .requestFactory(requestFactory);
    }

    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory()
            .withClient("cliente-app")
            .secret("123456")
            .scopes("read", "write")
            .resourceIds(RESOURCE_ID)
            .authorities("read", "write")
            .authorizedGrantTypes(
                "password",
                "authorization_code",
                "refresh_token");
    }

}
