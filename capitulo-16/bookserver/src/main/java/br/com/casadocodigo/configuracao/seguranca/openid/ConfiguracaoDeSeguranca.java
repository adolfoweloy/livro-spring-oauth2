package br.com.casadocodigo.configuracao.seguranca.openid;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

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
