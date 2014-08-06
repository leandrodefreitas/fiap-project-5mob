package br.com.fiap.sistema.conta;

import br.com.fiap.sistema.interfaces.Tributavel;


public class ContaCorrente extends Conta implements Tributavel {
	
	public ContaCorrente(int numero, int agencia, int senha, String nome) {
		super(numero, agencia, senha, nome);
	}

	@Override
	public double calculaTributos() {
		return this.getSaldo() * 0.01;
	}
}
