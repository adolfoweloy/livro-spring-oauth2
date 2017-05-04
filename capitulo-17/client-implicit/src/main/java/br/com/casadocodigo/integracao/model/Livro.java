package br.com.casadocodigo.integracao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Livro {

    @Getter @Setter
    private int id;

    @Getter @Setter
    private String titulo;

    @Getter @Setter
    private int nota;
}
