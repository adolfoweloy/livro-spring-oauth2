package br.com.casadocodigo.integracao.bookserver;

public class UsuarioSemAutorizacaoException extends Exception {

	private static final long serialVersionUID = -9208104861039137471L;

	// péssima prática utilizando exception (usando apenas com propósitos de teste)
    public UsuarioSemAutorizacaoException(String mensagem) {
        super(mensagem);
    }

}
