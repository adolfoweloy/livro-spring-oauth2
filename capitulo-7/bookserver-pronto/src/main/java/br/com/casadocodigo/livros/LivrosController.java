package br.com.casadocodigo.livros;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.configuracao.seguranca.ResourceOwner;
import br.com.casadocodigo.usuarios.Usuario;
import br.com.casadocodigo.usuarios.Usuarios;

@Controller
@RequestMapping("/livros")
public class LivrosController {

	@Autowired
	private Usuarios usuarios;

	@RequestMapping(value = "/principal", method = RequestMethod.GET)
	public ModelAndView principal() {
		ModelAndView mv = new ModelAndView("livros/principal");

		mv.addObject("dadosDoLivro", new DadosDoLivro());
		mv.addObject("livros", donoDosLivros().getEstante().todosLivros());

		return mv;
	}

	@RequestMapping(value = "/principal", method = RequestMethod.POST)
	public ModelAndView adicionarLivro(@Valid DadosDoLivro dadosDoLivro, BindingResult bindingResult) {
		ModelAndView mv = new ModelAndView("livros/principal");

		Usuario usuario = donoDosLivros();

		if (bindingResult.hasErrors()) {
			mv.addObject("livros", usuario.getEstante().todosLivros());
			mv.addObject("dadosDoLivro", dadosDoLivro);
			return mv;
		}

		Livro novoLivro = new Livro(dadosDoLivro.getTitulo(), dadosDoLivro.getNota());
		usuario.getEstante().adicionar(novoLivro);

		usuarios.atualizar(usuario);

		mv.addObject("livros", usuario.getEstante().todosLivros());
		mv.addObject("dadosDoLivro", new DadosDoLivro());

		return mv;
	}

	private Usuario donoDosLivros() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ResourceOwner donoDosLivros = (ResourceOwner) authentication.getPrincipal();

		return usuarios.buscarPorID(donoDosLivros.getId());
	}
}
