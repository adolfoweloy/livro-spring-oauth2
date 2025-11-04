package br.com.casadocodigo.livros;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
import org.hibernate.validator.constraints.Range;

@Entity
public class Livro {

	@Getter
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Getter
	private String titulo;
	
	@Getter
	@Range(min = 0, max = 10)
	private int nota;

	@Deprecated
	Livro() { }

	public Livro(String titulo, int nota) {
		super();
		this.titulo = titulo;
		this.nota = nota;
	}
	
}
