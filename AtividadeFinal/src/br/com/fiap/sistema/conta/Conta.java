package br.com.fiap.sistema.conta;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

import br.com.fiap.sistema.exceptions.SaldoInsuficienteException;
import br.com.fiap.sistema.exceptions.ValorInvalidoException;
import br.com.fiap.sistema.util.UtilProperties;


public abstract class Conta{

	protected double saldo = 10000;
	private int numero;
	private int agencia;
	private int senha;
	private String nome;
	private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();	

	/**
	 * 
	 * @param numero
	 * @param agencia
	 * @param senha
	 * @param nome
	 */
	public Conta(int numero, int agencia, int senha, String nome) {
		super();
		this.numero = numero;
		this.agencia = agencia;
		this.senha = senha;
		this.nome = nome;
	}

	/**
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * 
	 * @return
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * 
	 * @return
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * 
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAgencia() {
		return agencia;
	}

	/**
	 * 
	 * @param agencia
	 */
	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	public int getSenha() {
		return senha;
	}

	/**
	 * 
	 * @param senha
	 */
	public void setSenha(int senha) {
		this.senha = senha;
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
	 * @param valor
	 */
	public void saca(double valor) {
		if (valor > this.saldo) {
			throw new SaldoInsuficienteException(valor);
		} else {
			this.saldo -= valor;
			Movimentacao m = new Movimentacao(new Date(), valor, this.saldo, Movimento.DEBITO);
			addMovimentacao(m);
		}
	}

	/**
	 * 
	 * @param valor
	 * @throws ValorInvalidoException
	 */
	public void deposita(double valor) throws ValorInvalidoException {
		if (valor < 0) {
			throw new ValorInvalidoException(valor);
		} else {
			this.saldo += valor;
			Movimentacao m = new Movimentacao(new Date(), valor, this.saldo, Movimento.CREDITO);
			addMovimentacao(m);
		}
	}

	/**
	 * 
	 * @param m
	 */
	public void addMovimentacao(Movimentacao m){
		this.movimentacoes.add(m);
	}



	/**
	 * 
	 * @throws IOException
	 */
	public void emiteExtratoTela() throws IOException{

		Properties prop = UtilProperties.getProp();

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");	
		SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date d = new Date();

		// cabeçalho
		System.out.printf("\n%45s\n",prop.getProperty("prop.conta.cabecalho.titulo"));
		System.out.printf("%-39s%21s\n",prop.getProperty("prop.conta.cabecalho.extratoContaCorrente"),formatador2.format(d));
		System.out.printf(
				"%-20s%-20s%.20s \n",
				prop.getProperty("prop.conta.cabecalho.agencia") + this.agencia,
				prop.getProperty("prop.conta.cabecalho.conta") + this.numero,
				prop.getProperty("prop.conta.cabecalho.cliente") + this.nome
				);	
		System.out.printf("%-15s%-17s%-14s%14s\n",
				prop.getProperty("prop.conta.cabecalho.data"),
				prop.getProperty("prop.conta.cabecalho.descricao"),
				prop.getProperty("prop.conta.cabecalho.valor"),
				prop.getProperty("prop.conta.cabecalho.saldo")
				);
		System.out.println(prop.getProperty("prop.conta.layout.barra"));
		// imprime movimentações
		for (Movimentacao m : this.movimentacoes) {
			System.out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		System.out.println(prop.getProperty("prop.conta.layout.barra"));
		System.out.printf("%-40s%20.2f \n",
				prop.getProperty("prop.conta.cabecalho.saldoFinal"),this.saldo);	
	}

	/**
	 * 
	 * @throws IOException
	 */
	public void emiteExtratoArquivo() throws IOException{
		
		Properties prop = UtilProperties.getProp();

		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");	
		SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date d = new Date();

		FileWriter fw = new FileWriter("extrato.txt");
		PrintWriter out = new PrintWriter(fw);

		out.printf("%45s \n","Sistema de Auto-Atendimento");
		out.printf("%-39s%21s \n","Extrato de Conta Corrente",formatador2.format(d));
		out.printf("%-20s%-20s%.20s \n",
				prop.getProperty("prop.conta.cabecalho.agencia") + this.agencia,
				prop.getProperty("prop.conta.cabecalho.conta") + this.numero,
				prop.getProperty("prop.conta.cabecalho.cliente") + this.nome
				);			
		out.printf("%-15s%-17s%-14s%14s\n",
				prop.getProperty("prop.conta.cabecalho.data"),
				prop.getProperty("prop.conta.cabecalho.descricao"),
				prop.getProperty("prop.conta.cabecalho.valor"),
				prop.getProperty("prop.conta.cabecalho.saldo")
				);
		out.println(prop.getProperty("prop.conta.layout.barra"));			
		for (Movimentacao m : this.movimentacoes) {
			out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		out.println(prop.getProperty("prop.conta.layout.barra"));	
		out.printf("%-40s%20.2f \n\n",
				prop.getProperty("prop.conta.cabecalho.saldoFinal"),1000.004);		

		out.close();

	}

}
