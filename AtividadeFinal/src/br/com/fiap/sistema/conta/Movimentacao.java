package br.com.fiap.sistema.conta;

import java.util.Date;

public class Movimentacao {

	private Date data;	
	private double valor;
	private double saldo;	
	private Movimento movimento;

	/**
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
	 * 
	 * @return
	 */
	public Date getData() {
		return data;
	}

	/**
	 * 
	 * @param data
	 */
	public void setData(Date data) {
		this.data = data;
	}

	/**
	 * 
	 * @return
	 */
	public double getValor() {
		return valor;
	}

	/**
	 * 
	 * @param valor
	 */
	public void setValor(double valor) {
		this.valor = valor;
	}

	/**
	 * 
	 * @return
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * 
	 * @param saldo
	 */
	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	/**
	 * 
	 * @return
	 */
	public Movimento getMovimento() {
		return movimento;
	}

	/**
	 * 
	 * @param movimento
	 */
	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}

}
