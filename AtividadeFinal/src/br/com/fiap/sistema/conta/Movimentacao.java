package br.com.fiap.sistema.conta;

import java.util.Date;

public class Movimentacao {
	
	private Date data;	
	private double valor;
	private double saldo;	
	private Movimento movimento;
	
	public Movimentacao(Date data, double valor, double saldo,
			Movimento movimento) {
		this.data = data;
		this.valor = valor;
		this.saldo = saldo;
		this.movimento = movimento;
	}
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getSaldo() {
		return saldo;
	}

	public void setSaldo(double saldo) {
		this.saldo = saldo;
	}

	public Movimento getMovimento() {
		return movimento;
	}

	public void setMovimento(Movimento movimento) {
		this.movimento = movimento;
	}
	
}
