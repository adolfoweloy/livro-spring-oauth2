package br.com.casadocodigo.configuracao.seguranca.openid;

import br.com.casadocodigo.usuarios.AutenticacaoOpenid;
import br.com.casadocodigo.usuarios.autenticacao.UsuarioAutenticado;
import org.springframework.security.core.Authentication;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@Transactional
public class OpenIdTokenServices implements ClientTokenServices {

    @Override
    public OAuth2AccessToken getAccessToken(OAuth2ProtectedResourceDetails resource,
        Authentication authentication) {
        if (authentication == null) return null;

        UsuarioAutenticado userDetails = (UsuarioAutenticado) authentication.getPrincipal();
        AutenticacaoOpenid autenticacaoOpenId = userDetails.getAutenticacaoOpenId();

        if (autenticacaoOpenId.expirou()) {
            return null;
        }

        return userDetails.getToken();
    }

    @Override
    public void saveAccessToken(OAuth2ProtectedResourceDetails resource,
        Authentication authentication, OAuth2AccessToken accessToken) {

    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource,
        Authentication authentication) {
        throw new UnsupportedOperationException("Operação não suportada");
    }
}
