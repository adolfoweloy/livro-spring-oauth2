package br.com.casadocodigo.configuracao.seguranca.oauth;

import br.com.casadocodigo.configuracao.seguranca.ResourceOwner;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class DadosAdicionaisEnhancer implements TokenEnhancer {

    @Override
    public OAuth2AccessToken enhance(
            OAuth2AccessToken accessToken,
            OAuth2Authentication authentication) {

        Map<String, Object> additionalInformation = new HashMap<>();
        ResourceOwner usuario = (ResourceOwner) authentication.getPrincipal();
        additionalInformation.put("nome_usuario", usuario.getUsuario().getNome());

        DefaultOAuth2AccessToken defaultAccessToken
                = (DefaultOAuth2AccessToken) accessToken;

        defaultAccessToken.setAdditionalInformation(additionalInformation);

        return defaultAccessToken;
    }

}
