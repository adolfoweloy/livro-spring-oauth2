package br.com.casadocodigo.usuarios;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Date;

@Entity
public class AutenticacaoOpenid implements Serializable {
	private static final long serialVersionUID = 1L;

	@Getter
    @Id @Embedded
    private IdentificadorDeAutorizacao id;

    @Getter
    @Column(name = "authn_provider")
    private String provider;

    @Getter @Setter
    @Column(name = "authn_validade")
    private Date validade;

    @Getter
    @OneToOne(cascade = { CascadeType.PERSIST, CascadeType.MERGE })
    private Usuario usuario;

    @Deprecated
    AutenticacaoOpenid() {
    }

    public AutenticacaoOpenid(Usuario usuario, IdentificadorDeAutorizacao id, String provider, Date validade) {
        this.id = id;
        this.provider = provider;
        this.validade = validade;
        this.usuario = usuario;

        usuario.autenticar(this);
    }

    public boolean expirou() {
        OffsetDateTime dataDeValidadeDoToken = OffsetDateTime.ofInstant(
            validade.toInstant(), ZoneId.systemDefault());

        OffsetDateTime agora = OffsetDateTime.now(ZoneId.systemDefault());

        return agora.isAfter(dataDeValidadeDoToken);
    }
}
