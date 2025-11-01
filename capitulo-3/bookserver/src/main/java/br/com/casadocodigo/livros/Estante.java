package br.com.casadocodigo.livros;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import br.com.casadocodigo.usuarios.Usuario;

@Entity
public class Estante {

	@Getter
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@ManyToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	private Usuario usuario;

	@OneToMany(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinColumn(name = "estante_id")
	private List<Livro> livros = new ArrayList<>();

	public boolean temLivros() {
		return livros.size() > 0;
	}

	public Collection<Livro> todosLivros() {
		return Collections.unmodifiableCollection(livros);
	}

	public void adicionar(Livro livro) {
		livros.add(livro);
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
