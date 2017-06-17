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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.client.filter.OAuth2AuthenticationFailureEvent;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;

import javax.servlet.ServletException;
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
    private ClientTokenServices clientTokenServices;

    @Setter
    private OAuth2ProtectedResourceDetails oAuth2ProtectedResourceDetails;

    private ApplicationEventPublisher eventPublisher;

    public OpenIdConnectFilter(String defaultFilterProcessesUrl) {
        super(defaultFilterProcessesUrl);
        setAuthenticationManager(new NoopAuthenticationManager());
    }

    @Override
    public void setApplicationEventPublisher(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
        super.setApplicationEventPublisher(eventPublisher);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
        throws AuthenticationException, IOException, ServletException {

        OAuth2AccessToken accessToken;

        try {
            accessToken = restTemplate.getAccessToken();

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            clientTokenServices.saveAccessToken(oAuth2ProtectedResourceDetails, authentication, accessToken);

        } catch (OAuth2Exception e) {
            BadCredentialsException erro = new BadCredentialsException(
                    "Não foi possível obter o token", e);
            publish(new OAuth2AuthenticationFailureEvent(erro));
            throw erro;
        }

        try {
            TokenIdClaims tokenIdClaims = obterAsClaimsDoToken(accessToken);

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

    private TokenIdClaims obterAsClaimsDoToken(OAuth2AccessToken accessToken) {
        String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
        Jwt tokenDecoded = JwtHelper.decode(idToken);

        try {
            return jsonMapper.readValue(tokenDecoded.getClaims(), TokenIdClaims.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
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