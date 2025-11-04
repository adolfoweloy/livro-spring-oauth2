package br.com.casadocodigo.usuarios;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

public class DadosDeRegistro {

	@Getter
	@Setter
	@NotEmpty
	private String nome;

	@Getter
	@Setter
	@NotEmpty
	private String email;

	@Getter
	@Setter
	@NotEmpty
	private String senha;

}
