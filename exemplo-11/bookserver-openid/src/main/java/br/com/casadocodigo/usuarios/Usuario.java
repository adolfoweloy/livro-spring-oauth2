package br.com.casadocodigo.usuarios;

import javax.persistence.CascadeType;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import br.com.casadocodigo.livros.Estante;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Entity
public class Usuario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Getter
	private String nome;

	@Getter
	private String email;

	@Getter
	@OneToOne(mappedBy = "usuario", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private AutenticacaoOpenid autenticacaoOpenid;

	@Getter
	@OneToOne(mappedBy = "usuario", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Estante estante = new Estante();

	@Deprecated
	Usuario() {
		this.nome = "Anonimo";
		this.estante.setUsuario(this);
	}

	public Usuario(String nome, String email) {
		this.nome = nome;
		this.email = email;
		this.estante.setUsuario(this);
	}

	public void alterarNome(String nome) {
		this.nome = nome;
	}

	public void autenticar(AutenticacaoOpenid autenticacaoOpenid) {
		this.autenticacaoOpenid = autenticacaoOpenid;
	}
}
