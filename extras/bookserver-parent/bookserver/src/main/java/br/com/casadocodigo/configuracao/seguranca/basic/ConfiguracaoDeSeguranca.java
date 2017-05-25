package br.com.casadocodigo.configuracao.seguranca.basic;

import br.com.casadocodigo.configuracao.seguranca.DadosDoUsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

	@Configuration
	@Order(1)
	public static class ConfiguracaoParaAPI extends WebSecurityConfigurerAdapter {
		@Autowired
		private DadosDoUsuarioService userAuthenticationService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userAuthenticationService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.antMatcher("/api/**")
					.httpBasic()
					.and()
				.csrf().disable();
			// @formatter:on
		}
	}

	@Configuration
	public static class ConfiguracaoParaUsuario extends WebSecurityConfigurerAdapter {
		@Autowired
		private DadosDoUsuarioService userAuthenticationService;

		@Override
		protected void configure(AuthenticationManagerBuilder auth) throws Exception {
			auth.userDetailsService(userAuthenticationService);
		}

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers("/", "/home", "/usuarios", "/webjars/**", "/static/**", "/jquery*").permitAll()
					.anyRequest().authenticated().and()
				.formLogin()
					.permitAll()
					.loginPage("/login")
					.and()
				.logout()
					.permitAll()
					.and()
				.csrf().disable();
			// @formatter:on
		}
	}
}
