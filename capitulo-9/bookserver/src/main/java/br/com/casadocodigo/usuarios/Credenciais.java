package br.com.casadocodigo.usuarios;

import javax.persistence.Embeddable;

import lombok.Getter;

@Embeddable
public class Credenciais {

	@Getter
	private String email;
	
	@Getter
	private String senha;

	@Deprecated
	Credenciais() {}
	
	public Credenciais(String email, String senha) {
		super();
		this.email = email;
		this.senha = senha;
	}
	
}
