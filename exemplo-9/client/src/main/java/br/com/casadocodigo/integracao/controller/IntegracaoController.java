package br.com.casadocodigo.integracao.controller;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.integracao.bookserver.AuthorizationCodeTokenService;
import br.com.casadocodigo.integracao.bookserver.OAuth2Token;
import br.com.casadocodigo.usuarios.AcessoBookserver;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.client.OAuth2RestOperations;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.annotation.ApplicationScope;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/integracao")
@RequestScope
public class IntegracaoController {

    @Autowired
    private UsuariosRepository usuarios;

    @Autowired
    private AuthorizationCodeTokenService authorizationCodeTokenService;

    @Autowired
    private OAuth2RestOperations restTemplate;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {

        String authorizationEndpoint = authorizationCodeTokenService
                .getAuthorizationEndpoint();

        return new ModelAndView("redirect:" + authorizationEndpoint);
    }

    @RequestMapping(method = RequestMethod.GET, value = "/callback")
    public ModelAndView autorizar(String code) {

        OAuth2AccessToken accessToken = restTemplate.getAccessToken();

        return new ModelAndView("redirect:/minhaconta/principal");
    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
