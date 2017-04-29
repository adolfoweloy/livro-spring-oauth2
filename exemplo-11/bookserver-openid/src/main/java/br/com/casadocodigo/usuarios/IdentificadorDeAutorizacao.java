package br.com.casadocodigo.usuarios;

import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
public class IdentificadorDeAutorizacao implements Serializable {

    @Getter
    @Column(name = "authn_id")
    private String valor;

    IdentificadorDeAutorizacao() {}

    public IdentificadorDeAutorizacao(String valor) {
        this.valor = valor;
    }

}
