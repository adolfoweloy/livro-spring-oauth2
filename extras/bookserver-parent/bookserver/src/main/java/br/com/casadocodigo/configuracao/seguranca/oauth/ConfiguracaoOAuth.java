package br.com.casadocodigo.configuracao.seguranca.oauth;

import br.com.casadocodigo.configuracao.seguranca.conditions.OAuthHabilitado;
import br.com.casadocodigo.configuracao.seguranca.conditions.OAuthProviderJunto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Conditional;
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
@Conditional({
    OAuthHabilitado.class,
    OAuthProviderJunto.class
})
public class ConfiguracaoOAuth {

    public static final String RESOURCE_ID = "books";

    @EnableResourceServer
    protected static class OAuth2ResourceServer
            extends ResourceServerConfigurerAdapter {

        @Override
        public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
            resources.resourceId(RESOURCE_ID);
        }

        @Override
        public void configure(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .requestMatchers()
                    .antMatchers("/api/v2/livros/**").and()
                .authorizeRequests()
                    .antMatchers("/api/v2/livros/**").authenticated();
            // @formatter:on
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
        public void configure(ClientDetailsServiceConfigurer clients)
                throws Exception {

            // @formatter:off
            clients
                .inMemory()
                .withClient("bookserver-client")
                .secret("123456")
                .authorizedGrantTypes("password")
                .scopes("read", "write")
                .resourceIds(RESOURCE_ID);
            // @formatter:on
        }

    }

}
