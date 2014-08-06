package br.com.fiap.sistema.exceptions;

public class SaldoInsuficienteException extends RuntimeException{

	
	public SaldoInsuficienteException(double valor) {
		super("Saldo insuficiente na conta. Opera��o cancelada. \n");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
