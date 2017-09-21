package br.com.casadocodigo.configuracao.seguranca.openid;

import br.com.casadocodigo.usuarios.IdentificadorDeAutorizacao;
import br.com.casadocodigo.usuarios.RepositorioDeUsuarios;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.autenticacao.UsuarioAutenticado;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OpenIdConnectFilter extends AbstractAuthenticationProcessingFilter {

    @Setter
    private OAuth2RestTemplate restTemplate;

    @Setter
    private ObjectMapper jsonMapper;

    @Setter
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Setter
    private OpenIdTokenServices tokenServices;

    @Setter
    private OAuth2ProtectedResourceDetails resourceDetails;

    private ApplicationEventPublisher eventPublisher;

    private final RequestMatcher matcherLocal;

    public OpenIdConnectFilter(String defaultFilterProcessesUrl, String callback) {
        super(new OrRequestMatcher(
                new AntPathRequestMatcher(defaultFilterProcessesUrl),
                new AntPathRequestMatcher(callback)));
        this.matcherLocal = new AntPathRequestMatcher(defaultFilterProcessesUrl);
        setAuthenticationManager(new NoopAuthenticationManager());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        super.setApplicationEventPublisher(eventPublisher);
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;

        if (matcherLocal.matches(request)) {
            restTemplate.getAccessToken();
            chain.doFilter(req, res);
        } else {
            super.doFilter(req, res, chain);
        }
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        OAuth2AccessToken accessToken;

        try {
            accessToken = restTemplate.getAccessToken();
            tokenServices.saveAccessToken(accessToken);

        } catch (OAuth2Exception e) {
            BadCredentialsException erro = new BadCredentialsException(
                    "Não foi possível obter o token", e);
            publish(new OAuth2AuthenticationFailureEvent(erro));
            throw erro;
        }

        try {
            TokenIdClaims tokenIdClaims = TokenIdClaims.extrairClaims(jsonMapper, accessToken);

            Usuario usuario = repositorioDeUsuarios.buscarUsuarioAutenticado(
                new IdentificadorDeAutorizacao(tokenIdClaims.getSubjectIdentifier())).get();

            UsuarioAutenticado usuarioAutenticado = new UsuarioAutenticado(
                usuario.getAutenticacaoOpenid(), accessToken);

            Authentication authentication = new UsernamePasswordAuthenticationToken(
                usuarioAutenticado, null, usuarioAutenticado.getAuthorities());

            publish(new AuthenticationSuccessEvent(authentication));
            return authentication;

        } catch (InvalidTokenException e) {
            BadCredentialsException erro = new BadCredentialsException(
                    "Não foi possível obter os detalhes do token", e);
            publish(new OAuth2AuthenticationFailureEvent(erro));
            throw erro;
        }
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