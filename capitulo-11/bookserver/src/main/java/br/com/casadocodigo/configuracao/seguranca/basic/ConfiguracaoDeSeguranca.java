package br.com.casadocodigo.configuracao.seguranca.basic;

import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

	@Configuration
	@Order(1)
	public static class ConfiguracaoParaAPI extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {
			// @formatter:off
			http
				.authorizeRequests()
					.anyRequest().authenticated().and()
				.antMatcher("/api/livros")
					.httpBasic()
					.and()
				.csrf().disable();
			// @formatter:on
		}

	}

	@Configuration
	public static class ConfiguracaoParaUsuario extends WebSecurityConfigurerAdapter {

		@Override
		protected void configure(HttpSecurity http) throws Exception {

			String[] caminhosPermitidos = new String[] {
				"/", "/home", "/usuarios",
				"/webjars/**", "/static/**", "/jquery*"
			};

			// @formatter:off
			http
				.authorizeRequests()
					.antMatchers(caminhosPermitidos).permitAll()
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
