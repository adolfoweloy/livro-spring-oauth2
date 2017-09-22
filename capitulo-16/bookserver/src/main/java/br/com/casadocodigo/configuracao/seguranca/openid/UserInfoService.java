package br.com.casadocodigo.configuracao.seguranca.openid;

import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class UserInfoService {

	@SuppressWarnings({ "rawtypes", "unchecked" })
    public Map<String, String> getUserInfoFor(OAuth2AccessToken accessToken) {
        RestTemplate restTemplate = new RestTemplate();

        // Implementar a lógica para obter userInfo


        throw new UnsupportedOperationException("Lógica do userInfo ainda não implementada");
    }

    private MultiValueMap getHeader(String accessToken) {
        MultiValueMap<String, String> httpHeaders = new HttpHeaders();

        httpHeaders.add("Authorization", "Bearer " + accessToken);

        return httpHeaders;
    }

}
