package br.com.casadocodigo.configuracao.seguranca.openid;

import javax.transaction.Transactional;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class OpenIdTokenServices {

    public void saveAccessToken(OAuth2AccessToken accessToken) {

    }

}
