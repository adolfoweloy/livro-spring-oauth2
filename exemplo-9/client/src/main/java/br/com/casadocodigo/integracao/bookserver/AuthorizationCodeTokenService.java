package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.configuracao.seguranca.BasicAuthentication;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AuthorizationCodeTokenService {

    public String getAuthorizationEndpoint() {

        BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-app", "123456");

        String endpointDeAutorizacao = "http://localhost:8080/oauth/authorize";

        // montando a URI
        Map<String, String> parametros = new HashMap<>();
        parametros.put("client_id", getEncodedUrl(clientAuthentication.getLogin()));
        parametros.put("response_type", "code");
        parametros.put("redirect_uri", getEncodedUrl("http://localhost:9000/integracao/callback"));
        parametros.put("scope", getEncodedUrl("read write"));

        return construirUrl(endpointDeAutorizacao, parametros);

    }

    private String construirUrl(String endpointDeAutorizacao, Map<String, String> parametros) {
        List<String> parametrosDeAutorizacao = new ArrayList<>(parametros.size());

        parametros.forEach((param, valor) -> {
            parametrosDeAutorizacao.add(param + "=" + valor);
        });

        return endpointDeAutorizacao + "?" + parametrosDeAutorizacao.stream()
                .reduce((a,b) -> a + "&" + b).get();
    }

    private String getEncodedUrl(String url) {
        try {
            return URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException(e);
        }
    }

    public OAuth2Token getToken(String authorizationCode) {
        RestTemplate restTemplate = new RestTemplate();
        BasicAuthentication clientAuthentication = new BasicAuthentication("cliente-app", "123456");

        RequestEntity<MultiValueMap<String, String>> requestEntity = new RequestEntity<>(
                getBody(authorizationCode),
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

    private MultiValueMap<String, String> getBody(String authorizationCode) {
        MultiValueMap<String, String> dadosFormulario = new LinkedMultiValueMap<>();

        dadosFormulario.add("grant_type", "authorization_code");
        dadosFormulario.add("code", authorizationCode);
        dadosFormulario.add("scope", "read write");
        dadosFormulario.add("redirect_uri",
                "http://localhost:9000/integracao/callback");

        return dadosFormulario;
    }

    private HttpHeaders getHeader(BasicAuthentication clientAuthentication) {
        HttpHeaders httpHeaders = new HttpHeaders();

        httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        httpHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        httpHeaders.add("Authorization", "Basic " + clientAuthentication.getCredenciaisBase64());

        return httpHeaders;
    }

}
