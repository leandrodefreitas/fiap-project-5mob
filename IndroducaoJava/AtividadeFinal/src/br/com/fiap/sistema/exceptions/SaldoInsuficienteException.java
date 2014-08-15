package br.com.fiap.sistema.exceptions;

public class SaldoInsuficienteException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4968563189450809791L;

	/**
	 * 
	 * @param valor
	 */
	public SaldoInsuficienteException(double valor) {
		super("Saldo insuficiente na conta. Operação cancelada. \n");
	}

}
