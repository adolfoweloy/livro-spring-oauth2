package br.com.casadocodigo.usuarios;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import br.com.casadocodigo.configuracao.seguranca.ResourceOwner;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	@Autowired
	private Usuarios usuarios;

	@Autowired
	private AuthenticationManager authenticationManager;

	/**
	 * Tela de cadastro
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET)
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("usuarios/cadastro");

		DadosDeRegistro dadosDeRegistro = new DadosDeRegistro();
		mv.addObject("dadosDeRegistro", dadosDeRegistro);

		return mv;
	}

	/**
	 * Método que recebe os dados do formulário de cadastro de usuário
	 * @param dadosDeRegistro
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	public ModelAndView registrar(@Valid DadosDeRegistro dadosDeRegistro, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("usuarios/cadastro");
		}

		// cria um usuario no sistema
		Usuario usuario = new Usuario(dadosDeRegistro.getNome(),
			new Credenciais(dadosDeRegistro.getEmail(), dadosDeRegistro.getSenha()));

		// persiste os dados do usuario
		usuarios.registrar(usuario);

		// autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
		mantemUsuarioAutenticado(authenticationManager, usuario);

		// usuário cadastrado é redirecionado para página de controle de livros
		ModelAndView mv = new ModelAndView("redirect:/livros/principal");

		return mv;
	}

	/**
	 * Esse método é usado apenas para adicionar o usuário recem cadastrado na sessão do Spring Security para que
	 * o usuário não precise se autenticar assim que se cadastra.
	 * @param authenticationManager
	 * @param usuario
	 */
	private void mantemUsuarioAutenticado(AuthenticationManager authenticationManager, Usuario usuario) {
		Authentication auth = new UsernamePasswordAuthenticationToken(
			new ResourceOwner(usuario), usuario.getCredenciais().getSenha());
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));
	}

}
