package br.com.fiap.sistema.exceptions;

public class ValorInvalidoException extends RuntimeException{

	public ValorInvalidoException(double valor) {
		super("Valor inv�lido: " + valor + ". Opera��o cancelada. \n");
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
