package br.com.casadocodigo.integracao.bookserver;

import java.net.URI;
import java.util.Arrays;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import br.com.casadocodigo.configuracao.seguranca.BasicAuthentication;

@Service
public class PasswordTokenService {

    public OAuth2Token getToken(String loginDoUsuario, String senhaDoUsuario) {

        RestTemplate restTemplate = new RestTemplate();
        BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-app", "123456");

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(loginDoUsuario, senhaDoUsuario),
                getHeader(clientAuthentication),
                HttpMethod.POST,
                URI.create("http://localhost:8080/oauth/token")
        );

        ResponseEntity<OAuth2Token> responseEntity = restTemplate.exchange(
                requestEntity,
                OAuth2Token.class);

        if (responseEntity.getStatusCode().is2xxSuccessful()) {
            return responseEntity.getBody();
        }

        // isso deve ser tratado de forma melhor (apenas para exemplo)
        throw new RuntimeException("error trying to retrieve access token");
    }

    private MultiValueMap<String, String> getBody(String loginDoUsuario, String senhaDoUsuario) {
        MultiValueMap<String, String> dadosFormulario = new LinkedMultiValueMap<>();
        dadosFormulario.add("grant_type", "password");
        dadosFormulario.add("username", loginDoUsuario);
        dadosFormulario.add("password", senhaDoUsuario);
        dadosFormulario.add("scope", "read write");
        return dadosFormulario;
    }

    private HttpHeaders getHeader(BasicAuthentication clientAuthentication) {
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization",  "Basic " + clientAuthentication.getCredenciaisBase64());
        return httpHeaders;
    }
}
