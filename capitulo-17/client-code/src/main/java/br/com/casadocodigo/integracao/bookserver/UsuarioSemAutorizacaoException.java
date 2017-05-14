package br.com.casadocodigo.integracao.bookserver;

public class UsuarioSemAutorizacaoException extends Exception {

	private static final long serialVersionUID = 1L;

	// péssima prática utilizando exception (usando apenas com propósitos de teste)
    public UsuarioSemAutorizacaoException(String mensagem) {
        super(mensagem);
    }

}
