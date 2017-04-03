package br.com.casadocodigo.integracao.bookserver;

import br.com.casadocodigo.configuracao.seguranca.BasicAuthentication;
import br.com.casadocodigo.integracao.model.Livro;
import br.com.casadocodigo.usuarios.Usuario;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.client.OAuth2RestTemplate;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class BookserverService {

    @Autowired
    private OAuth2RestOperations oAuth2RestTemplate;

    public List<Livro> livrosFrom(Usuario usuario) throws UsuarioSemAutorizacaoException {

        String endpoint = "http://localhost:8080/api/v2/livros";

        try {
//            oAuth2RestTemplate

            String token = usuario.getAcessoBookserver().getAcessoToken();
            JsonNode resposta = oAuth2RestTemplate.getForObject(endpoint, JsonNode.class);

            System.out.println(resposta);
            return null;
//            if (resposta.getStatusCode().is2xxSuccessful()) {
//                return listaFromArray(resposta.getBody());
//            } else {
//                throw new RuntimeException("sem sucesso");
//            }
        } catch (HttpClientErrorException e) {
            System.out.println("erro" + e.getMessage());
            throw new UsuarioSemAutorizacaoException("não foi possível obter os livros do usuário");
        }

    }

    private List<Livro> listaFromArray(Livro[] livros) {
        List<Livro> lista = new ArrayList<>();

        for (Livro livro : livros) {
            lista.add(livro);
        }

        return lista;
    }

}
