package br.com.casadocodigo.configuracao.seguranca.openid;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.jwt.Jwt;
import org.springframework.security.jwt.JwtHelper;
import org.springframework.security.oauth2.common.OAuth2AccessToken;

import java.io.IOException;

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

	public static TokenIdClaims extrairClaims(ObjectMapper jsonMapper,  OAuth2AccessToken accessToken) {
		String idToken = accessToken.getAdditionalInformation().get("id_token").toString();
		Jwt tokenDecoded = JwtHelper.decode(idToken);

		try {
			return jsonMapper.readValue(tokenDecoded.getClaims(), TokenIdClaims.class);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

	}
}
