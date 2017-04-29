package br.com.casadocodigo.configuracao.seguranca.openid;

import br.com.casadocodigo.configuracao.seguranca.openid.OpenIdConnectFilter;
import br.com.casadocodigo.livros.RepositorioDeLivros;
import br.com.casadocodigo.usuarios.RepositorioDeUsuarios;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.filter.OAuth2ClientContextFilter;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.preauth.AbstractPreAuthenticatedProcessingFilter;

@EnableWebSecurity
public class ConfiguracaoDeSeguranca extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		String[] caminhosPermitidos = new String[] {
				"/", "/home", "/usuarios", "/google/login",
				"/webjars/**", "/static/**", "/jquery*"
		};

	}

}
