package br.com.casadocodigo.configuracao.seguranca.openid;

import br.com.casadocodigo.usuarios.AutenticacaoOpenid;
import br.com.casadocodigo.usuarios.IdentificadorDeAutorizacao;
import br.com.casadocodigo.usuarios.RepositorioDeUsuarios;
import br.com.casadocodigo.usuarios.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class OpenIdTokenServices {

    @Autowired
    private RepositorioDeUsuarios repositorioDeUsuarios;

    @Autowired
    private ObjectMapper jsonMapper;

    @Autowired
    private UserInfoService userInfoService;

    public void saveAccessToken(OAuth2AccessToken accessToken) {
        TokenIdClaims tokenIdClaims = TokenIdClaims.extrairClaims(jsonMapper, accessToken);

        Optional<Usuario> usuarioAutenticado = repositorioDeUsuarios.buscarUsuarioAutenticado(
            new IdentificadorDeAutorizacao(tokenIdClaims.getSubjectIdentifier()));

        Usuario usuario = usuarioAutenticado.orElseGet(() -> {
            Usuario novoUsuario = new Usuario(tokenIdClaims.getEmail(), tokenIdClaims.getEmail());

            new AutenticacaoOpenid(
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

    private Date obterDatetime(long timestamp) {
        return new Date(timestamp * 1000);
    }

}
