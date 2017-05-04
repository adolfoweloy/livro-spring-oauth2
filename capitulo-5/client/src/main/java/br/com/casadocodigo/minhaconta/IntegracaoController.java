package br.com.casadocodigo.minhaconta;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.usuarios.AcessoBookserver;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/integracao")
public class IntegracaoController {

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView integracao() {
        return new ModelAndView("minhaconta/integracao");
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView autorizar(Autorizacao autorizacao) {

        AcessoBookserver acessoBookserver = new AcessoBookserver();
        acessoBookserver.setLogin(autorizacao.getLogin());
        acessoBookserver.setSenha(autorizacao.getSenha());

        Usuario usuario = usuarioLogado();
        usuario.setAcessoBookserver(acessoBookserver);

        usuarios.save(usuario);

        return new ModelAndView("redirect:/minhaconta/principal");
    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
