package br.com.casadocodigo.configuracao.seguranca.oauth;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

@Configuration
public class ConfiguracaoOAuth2 {
    public static final String RESOURCE_ID = "books";

    @EnableResourceServer
    protected static class OAuth2ResourceServer extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources)
            throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            http
                .authorizeRequests()
                    .anyRequest().authenticated().and()
                .requestMatchers()
                    .antMatchers("/api/v2/**");
        }
    }

    @EnableAuthorizationServer
    protected static class OAuth2AuthorizationServer
        extends AuthorizationServerConfigurerAdapter {

        @Autowired
        private AuthenticationManager authenticationManager;

        @Override
        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
            // @formatter:off
            endpoints
                .authenticationManager(authenticationManager);
            // @formatter:on
        }

        @Override
        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
            clients.inMemory()
                .withClient("cliente-app")
                .secret("123456")
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID)
                .authorizedGrantTypes(
                    "password", "authorization_code")
            .and()
                .withClient("cliente-admin")
                .secret("123abc")
                .authorizedGrantTypes("client_credentials")
                .scopes("read")
                .resourceIds(RESOURCE_ID)
            .and()
                .withClient("cliente-browser")
                .authorizedGrantTypes("implicit")
                .scopes("read");
        }
    }
}
