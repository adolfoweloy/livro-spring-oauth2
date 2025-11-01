package br.com.casadocodigo.usuarios;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
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

	@Autowired
	private PasswordEncoder passwordEncoder;

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

		// Encode the password before creating the user
		String encodedPassword = passwordEncoder.encode(dadosDeRegistro.getSenha());

		// cria um usuario no sistema
		Usuario usuario = new Usuario(dadosDeRegistro.getNome(),
			new Credenciais(dadosDeRegistro.getEmail(), encodedPassword));

		// persiste os dados do usuario
		usuarios.registrar(usuario);

		// autentica o usuário recem-registrado para que o mesmo nao precise fazer o login
		// Use the original plain text password for authentication
		mantemUsuarioAutenticado(authenticationManager, usuario, dadosDeRegistro.getSenha());

		// usuário cadastrado é redirecionado para página de controle de livros
		ModelAndView mv = new ModelAndView("redirect:/livros/principal");

		return mv;
	}

	/**
	 * Esse método é usado apenas para adicionar o usuário recem cadastrado na sessão do Spring Security para que
	 * o usuário não precise se autenticar assim que se cadastra.
	 * @param authenticationManager
	 * @param usuario
	 * @param plainTextPassword - the original plain text password for authentication
	 */
	private void mantemUsuarioAutenticado(AuthenticationManager authenticationManager, Usuario usuario, String plainTextPassword) {
		Authentication auth = new UsernamePasswordAuthenticationToken(
			new ResourceOwner(usuario), plainTextPassword);
		SecurityContextHolder.getContext().setAuthentication(authenticationManager.authenticate(auth));
	}

}
