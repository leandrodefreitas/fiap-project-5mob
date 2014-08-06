package br.com.fiap.sistema.exceptions;

public class SaldoInsuficienteException extends RuntimeException{

	
	public SaldoInsuficienteException(double valor) {
		super("Saldo insuficiente na conta. Operação cancelada. \n");
	}
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
