package br.com.casadocodigo.usuarios;

import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString
public class Usuario {

    @Getter
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Getter
    private String nome;

    @Getter
    private String login;

    @Getter
    private String senha;

    @Embedded
    @Setter
    private AcessoBookserver acessoBookserver;

    @Deprecated
    Usuario() { }

    public Usuario(String nome, String login, String senha) {
        this.nome = nome;
        this.login = login;
        this.senha = senha;
    }

    public AcessoBookserver getAcessoBookserver() {
        if (acessoBookserver == null) {
            return new AcessoBookserverSemPermissao();
        }
        return acessoBookserver;
    }

    public class AcessoBookserverSemPermissao extends AcessoBookserver {
    }
}
