package br.com.casadocodigo.usuarios;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Calendar;

@Embeddable
@ToString
public class AcessoBookserver {

    @Getter @Setter
    @Column(name = "token_bookserver")
    private String acessoToken;

    @Getter @Setter
    @Column(name = "expiracao_token")
    private Calendar dataDeExpiracao;

}
