package br.com.casadocodigo.configuracao.seguranca.basic;

import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
public class ConfiguracaoDeSeguranca {

	@Configuration
	public static class ConfiguracaoParaUsuario {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
            // @formatter:off
            http
                .authorizeHttpRequests(authz -> authz
                    .requestMatchers(PathRequest.toStaticResources().atCommonLocations()).permitAll()
                    .anyRequest().authenticated()
                )
                .formLogin(login -> login
                    .loginPage("/login")
                    .defaultSuccessUrl("/livros/principal", true)
                    .failureUrl("/login?error=true")
                    .permitAll()
                )
                .logout(logout -> logout
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout=true")
                    .permitAll()
                )
                .csrf(AbstractHttpConfigurer::disable);
            // @formatter:on

            return http.build();
        }


        @Bean
        public PasswordEncoder passwordEncoder() {
            return new BCryptPasswordEncoder();
        }

	}
}
