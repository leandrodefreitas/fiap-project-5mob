package br.com.fiap.sistema.conta;

import java.util.Date;

public class Movimentacao {

	private Date data;	
	private double valor;
	private double saldo;	
	private Movimento movimento;

	/**
	 * Construtor da classe Movimentacao.
	 * 
	 * @param data
	 * @param valor
	 * @param saldo
	 * @param movimento
	 */
	public Movimentacao(Date data, double valor, double saldo,
			Movimento movimento) {
		this.data = data;
		this.valor = valor;
		this.saldo = saldo;
		this.movimento = movimento;
	}

	/**
	 * M�todo de consulta do atributo data.
	 * 
	 * @return
	 */
	public Date getData() {
		return data;
	}

	/**
	 * M�todo para alterar o atributo data.
	 * 
	 * @param data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * M�todo de consulta do atributo valor.
	 * 
	 * @return valor
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * M�todo para alterar o atributo valor.
	 * 
	 * @param valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * M�todo de consulta do atributo saldo.
	 * 
	 * @return saldo
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * M�todo para alterar o atributo saldo.
	 * 
	 * @param saldo
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * M�todo de consulta da classe Movimento.
	 * 
	 * @return
	 */
	public Movimento getMovimento() {
		return movimento;
	}

	/**
	 * M�todo para alterar o atributo movimento.
	 * 
	 * @param movimento
	 */
	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

}
