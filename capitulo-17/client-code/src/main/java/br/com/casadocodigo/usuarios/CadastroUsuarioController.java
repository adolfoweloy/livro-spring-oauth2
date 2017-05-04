package br.com.casadocodigo.usuarios;

import br.com.casadocodigo.configuracao.seguranca.UsuarioLogado;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class CadastroUsuarioController {

    @Autowired
    private UsuariosRepository usuariosRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @RequestMapping(method = RequestMethod.GET)
    public ModelAndView cadastro() {
        ModelAndView mv = new ModelAndView("usuarios/cadastro");

        DadosCadastrais dadosCadastrais = new DadosCadastrais();
        mv.addObject("dadosCadastrais", dadosCadastrais);

        return mv;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ModelAndView registrar(DadosCadastrais dadosCadastrais) {
        ModelAndView mv = new ModelAndView("redirect:/minhaconta/principal");

        Usuario usuario = new Usuario(dadosCadastrais.getNome(),
                dadosCadastrais.getLogin(), dadosCadastrais.getSenha());

        usuariosRepository.save(usuario);

        Authentication auth = new UsernamePasswordAuthenticationToken(new UsuarioLogado(usuario), usuario.getSenha());
        SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));

        return mv;
    }
}
