package br.com.casadocodigo.usuarios;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class DadosCadastrais {
    @Getter @Setter
    private String nome;

    @Getter @Setter
    private String login;

    @Getter @Setter
    private String senha;

}
