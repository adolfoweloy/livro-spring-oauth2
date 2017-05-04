package br.com.casadocodigo.minhaconta;

import br.com.casadocodigo.configuracao.seguranca.BasicAuthentication;
import br.com.casadocodigo.integracao.bookserver.BookserverService;
import br.com.casadocodigo.integracao.bookserver.UsuarioSemAutorizacaoException;
import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.UsuariosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/minhaconta")
public class MinhaContaController {

    @Autowired
    private BookserverService bookserverService;

    @Autowired
    private UsuariosRepository usuarios;

    @RequestMapping(value = "/principal")
    public ModelAndView principal() {

        Usuario usuario = usuarioLogado();
        String login = usuario.getAcessoBookserver().getLogin();
        String senha = usuario.getAcessoBookserver().getSenha();

        ModelAndView mv = new ModelAndView("minhaconta/principal");

        BasicAuthentication credenciais =
                new BasicAuthentication(login, senha);

        try {
            mv.addObject("livros", bookserverService.livros(credenciais));
        } catch (UsuarioSemAutorizacaoException e) {
            mv.addObject("erro", e.getMessage());
        }

        return mv;

    }

    private Usuario usuarioLogado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UsuarioLogado usuarioLogado = (UsuarioLogado) authentication.getPrincipal();
        return usuarios.findById(usuarioLogado.getId());
    }

}
