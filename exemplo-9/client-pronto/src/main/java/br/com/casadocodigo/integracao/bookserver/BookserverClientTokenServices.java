package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.usuarios.AcessoBookserver;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class BookserverClientTokenServices implements ClientTokenServices {

    @Autowired
    private UsuariosRepository usuarios;

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        Usuario usuario = usuarios.findById(usuarioLogado.getId());

        String accessToken = usuario.getAcessoBookserver().getAcessoToken();
        Calendar dataDeExpiracao = usuario.getAcessoBookserver().getDataDeExpiracao();

        if (accessToken == null) return null;

        DefaultOAuth2AccessToken oAuth2AccessToken
            = new DefaultOAuth2AccessToken(accessToken);
        oAuth2AccessToken.setExpiration(dataDeExpiracao.getTime());

        return oAuth2AccessToken;
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource,
            Authentication authentication, OAuth2AccessToken accessToken) {

        AcessoBookserver acessoBookserver = new AcessoBookserver();
        acessoBookserver.setAcessoToken(accessToken.getValue());

        Calendar expirationDate = Calendar.getInstance();
        expirationDate.setTime(accessToken.getExpiration());

        acessoBookserver.setDataDeExpiracao(expirationDate);

        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        Usuario usuario = usuarios.findById(usuarioLogado.getId());

        usuario.setAcessoBookserver(acessoBookserver);

        usuarios.save(usuario);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource, Authentication authentication) {
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        Usuario usuario = usuarios.findById(usuarioLogado.getId());

        usuario.setAcessoBookserver(null);
        usuarios.save(usuario);
    }
}
