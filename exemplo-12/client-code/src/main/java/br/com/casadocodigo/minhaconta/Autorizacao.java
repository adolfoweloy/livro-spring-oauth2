package br.com.casadocodigo.minhaconta;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
public class Autorizacao {

    @Getter @Setter
    private String login;

    @Getter @Setter
    private String senha;

}
