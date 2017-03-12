package br.com.casadocodigo.livros;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;
import org.hibernate.validator.constraints.Range;

@ToString
public class DadosDoLivro {

    @Getter
    @Setter
    @NotEmpty
    private String titulo;

    @Getter
    @Setter
    @Range(min = 0, max = 10)
    private int nota;

}
