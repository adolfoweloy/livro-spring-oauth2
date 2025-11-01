package br.com.casadocodigo.usuarios;

import jakarta.validation.Valid;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/usuarios")
public class UsuariosController {

	private final Usuarios usuarios;
	private final PasswordEncoder passwordEncoder;

    public UsuariosController(
        Usuarios usuarios,
        PasswordEncoder passwordEncoder
    ) {
        this.usuarios = usuarios;
        this.passwordEncoder = passwordEncoder;
    }

    /**
	 * Tela de cadastro
	 * @return ModelAndView
	 */
	@GetMapping
	public ModelAndView cadastro() {
		ModelAndView mv = new ModelAndView("usuarios/cadastro");

		var dadosDeRegistro = new DadosDeRegistro();
		mv.addObject("dadosDeRegistro", dadosDeRegistro);

		return mv;
	}

	/**
	 * Método que recebe os dados do formulário de cadastro de usuário
	 * @param dadosDeRegistro user registration data
	 * @return ModelAndView
	 */
	@PostMapping
	public ModelAndView registrar(@Valid DadosDeRegistro dadosDeRegistro, BindingResult bindingResult) {

		if (bindingResult.hasErrors()) {
			return new ModelAndView("usuarios/cadastro");
		}

		// Encode the password before creating the user
		var encodedPassword = passwordEncoder.encode(dadosDeRegistro.getSenha());

		// cria um usuario no sistema
		var usuario = new Usuario(
            dadosDeRegistro.getNome(),
            new Credenciais(dadosDeRegistro.getEmail(), encodedPassword)
        );

		// persiste os dados do usuario
		usuarios.registrar(usuario);

		// Redirect to login with success message - better security practice
		return new ModelAndView("redirect:/login?registered=true");
	}

}
