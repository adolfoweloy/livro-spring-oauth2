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
    @Column(name = "token_bookserver")
    private String acessoToken;

}
