package br.com.casadocodigo.livros;

import br.com.casadocodigo.configuracao.seguranca.ResourceOwner;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.Usuarios;
import jakarta.validation.Valid;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/livros")
public class LivrosController {

	private final Usuarios usuarios;

    LivrosController(Usuarios usuarios) {
        this.usuarios = usuarios;
    }

    @GetMapping("/principal")
	public ModelAndView principal(
        @AuthenticationPrincipal ResourceOwner resourceOwner
    ) {
		var mv = new ModelAndView("livros/principal");

		mv.addObject("dadosDoLivro", new DadosDoLivro());
		mv.addObject("livros", donoDosLivros(resourceOwner).getEstante().todosLivros());

		return mv;
	}

    @PostMapping("/principal")
	public ModelAndView adicionarLivro(
        @Valid DadosDoLivro dadosDoLivro,
        BindingResult bindingResult,
        @AuthenticationPrincipal ResourceOwner resourceOwner
    ) {
		var mv = new ModelAndView("livros/principal");

		var usuario = donoDosLivros(resourceOwner);

		if (bindingResult.hasErrors()) {
			mv.addObject("livros", usuario.getEstante().todosLivros());
			mv.addObject("dadosDoLivro", dadosDoLivro);
			return mv;
		}

		var novoLivro = new Livro(dadosDoLivro.getTitulo(), dadosDoLivro.getNota());
		usuario.getEstante().adicionar(novoLivro);

		usuarios.atualizar(usuario);

		mv.addObject("livros", usuario.getEstante().todosLivros());
		mv.addObject("dadosDoLivro", new DadosDoLivro());

		return mv;
	}

	private Usuario donoDosLivros(ResourceOwner resourceOwner) {
		return usuarios.buscarPorID(resourceOwner.getId());
	}
}
