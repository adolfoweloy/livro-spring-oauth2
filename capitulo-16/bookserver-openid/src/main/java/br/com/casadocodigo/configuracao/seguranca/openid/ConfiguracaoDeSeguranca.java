package br.com.casadocodigo.configuracao.seguranca.openid;

import br.com.casadocodigo.usuarios.RepositorioDeUsuarios;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@Configuration
@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

	@Autowired
	private OAuth2RestTemplate openidRestTemplate;

	@Autowired
	private ObjectMapper jsonMapper;

	@Autowired
	private RepositorioDeUsuarios repositorioDeUsuarios;

	@Autowired
	private OpenIdTokenServices tokenServices;

	@Autowired
	private OAuth2ProtectedResourceDetails resourceDetails;

	@Bean
	public OpenIdConnectFilter openIdConnectFilter() {
		OpenIdConnectFilter filter = new OpenIdConnectFilter(
			"/livros/**", "/google/callback");

		filter.setRestTemplate(openidRestTemplate);
		filter.setJsonMapper(jsonMapper);
		filter.setRepositorioDeUsuarios(repositorioDeUsuarios);
		filter.setTokenServices(tokenServices);
		filter.setResourceDetails(resourceDetails);
		return filter;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] caminhosPermitidos = new String[] {
				"/", "/home", "/usuarios", "/google/callback",
				"/webjars/**", "/static/**", "/jquery*"
		};

		http
			.addFilterAfter(filtroParaClientOAuth2(), AbstractPreAuthenticatedProcessingFilter.class)
			.addFilterAfter(openIdConnectFilter(), OAuth2ClientContextFilter.class)
			.httpBasic()
			.authenticationEntryPoint(new LoginUrlAuthenticationEntryPoint("/google/callback"))
		.and()
			.authorizeRequests()
			.antMatchers(caminhosPermitidos).permitAll()
			.anyRequest().authenticated()
		.and()
			.logout()
			.logoutSuccessUrl("/")
			.permitAll()
		.and()
			.csrf().disable();
	}

	private OAuth2ClientContextFilter filtroParaClientOAuth2() {
		return new OAuth2ClientContextFilter();
	}


}
