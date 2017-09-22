package br.com.casadocodigo.configuracao.seguranca.openid;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

    private ApplicationEventPublisher eventPublisher;

    protected OpenIdConnectFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        super.setApplicationEventPublisher(eventPublisher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        // implementar a lógica de autenticação via OpenID Connect Aqui
        throw new UnsupportedOperationException("Falta implementar a lógica de autenticação com OpenID Connect");
    }

    private void publish(ApplicationEvent event) {
        if (eventPublisher!=null) {
            eventPublisher.publishEvent(event);
        }
    }

    private static class NoopAuthenticationManager implements AuthenticationManager {

        @Override
        public Authentication authenticate(Authentication authentication)
                throws AuthenticationException {
            throw new UnsupportedOperationException("No authentication should be done with this AuthenticationManager");
        }

    }

}