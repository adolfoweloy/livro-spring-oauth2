package br.com.casadocodigo.usuarios;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
@ToString
public class AcessoBookserver {

    @Getter @Setter
    @Column(name = "login_bookserver")
    private String login;

    @Getter @Setter
    @Column(name = "senha_bookserver")
    private String senha;

}
