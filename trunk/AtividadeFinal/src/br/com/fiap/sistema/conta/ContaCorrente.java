package br.com.fiap.sistema.conta;

import br.com.fiap.sistema.interfaces.Tributavel;


public class ContaCorrente extends Conta implements Tributavel {

	/**
	 * Construtor da classe Conta Corrente.
	 * 
	 * @param numero
	 * @param agencia
	 * @param senha
	 * @param nome
	 */
	public ContaCorrente(int numero, int agencia, int senha, String nome) {
		super(numero, agencia, senha, nome);
	}

	/**
	 * Calcula o valor de 1% de tributo.
	 */
	@Override
	public double calculaTributos() {
		return this.getSaldo() * 0.01;
	}
}
