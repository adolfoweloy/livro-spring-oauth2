package br.com.casadocodigo.configuracao.seguranca.openid;

import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

import br.com.casadocodigo.usuarios.IdentificadorDeAutorizacao;
import br.com.casadocodigo.usuarios.autenticacao.UsuarioAutenticado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.client.resource.OAuth2ProtectedResourceDetails;
import org.springframework.security.oauth2.client.token.ClientTokenServices;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import br.com.casadocodigo.usuarios.AutenticacaoOpenid;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.RepositorioDeUsuarios;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.client.RestTemplate;

import javax.transaction.Transactional;

@Service
@Transactional
public class OpenIdTokenServices implements ClientTokenServices {

    @Autowired
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private UserInfoService userInfoService;

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

        TokenIdClaims tokenIdClaims = obterAsClaimsDoToken(accessToken);

        Optional<Usuario> usuarioAutenticado = repositorioDeUsuarios.buscarUsuarioAutenticado(
                new IdentificadorDeAutorizacao(tokenIdClaims.getSubjectIdentifier()));

        // recupera um usuário que já esteja autenticado ou cria um novo caso o mesmo nao tenha se registrado
        Usuario usuario = usuarioAutenticado.orElseGet(() -> {
            Usuario novoUsuario = new Usuario(tokenIdClaims.getEmail(), tokenIdClaims.getEmail());

            AutenticacaoOpenid autenticacaoOpenid = new AutenticacaoOpenid(
                    novoUsuario,
                    new IdentificadorDeAutorizacao(tokenIdClaims.getSubjectIdentifier()),
                    tokenIdClaims.getIssuerIdentifier(),
                    obterDatetime(tokenIdClaims.getExpirationTime())
            );

            return novoUsuario;
        });

        // se a conta do usuario expirou, atualiza com a nova data de validade
        if (usuario.getAutenticacaoOpenid().expirou()) {

            AutenticacaoOpenid autenticacaoOpenid = usuario.getAutenticacaoOpenid();
            autenticacaoOpenid.setValidade(obterDatetime(tokenIdClaims.getExpirationTime()));

        }

        // acessando o endpoint userinfo para obter o nome do usuário
        Map<String, String> userInfo = userInfoService.getUserInfoFor(accessToken);
        String nomeDoUsuario = userInfo.get("name");

        usuario.alterarNome(nomeDoUsuario);

        repositorioDeUsuarios.registrar(usuario);
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

    private Date obterDatetime(long timestamp) {
        return new Date(timestamp * 1000);
    }

    @Override
    public void removeAccessToken(OAuth2ProtectedResourceDetails resource,
        Authentication authentication) {
        throw new UnsupportedOperationException("Operação não suportada ainda");
    }
}
