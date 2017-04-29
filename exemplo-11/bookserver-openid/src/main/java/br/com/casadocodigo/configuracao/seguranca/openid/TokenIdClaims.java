package br.com.casadocodigo.configuracao.seguranca.openid;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;

public class TokenIdClaims {

	@Getter @Setter
	@JsonProperty("azp")
	private String authorizedParty;

	@Getter @Setter
	@JsonProperty("aud")
	private String audience;

	@Getter @Setter
	@JsonProperty("sub")
	private String subjectIdentifier;

	@Getter @Setter
	@JsonProperty("email")
	private String email;

	@Getter @Setter
	@JsonProperty("at_hash")
	private String accessTokenHashValue;

	@Getter @Setter
	@JsonProperty("iss")
	private String issuerIdentifier;

	@Getter @Setter
	@JsonProperty("iat")
	private long issuedAt;

	@Getter @Setter
	@JsonProperty("exp")
	private long expirationTime;

}
