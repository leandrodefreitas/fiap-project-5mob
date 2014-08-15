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

	// Saldo incial da conta no valor de R$ 10.000,00.
	protected double saldo = 10000;
	private int numero;
	private int agencia;
	private int senha;
	private String nome;
	private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();	

	/**
	 * Construtor da classe Conta.
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
	 * Método modificador do atributo nome.
	 * 
	 * @param nome
	 */
	public void setNome(String nome) {
		this.nome = nome;
	}

	/**
	 * Método de consulta do atributo nome.
	 * 
	 * @return nome
	 */
	public String getNome() {
		return nome;
	}

	/**
	 * Método de consulta do atributo numero.
	 * 
	 * @return numero
	 */
	public int getNumero() {
		return numero;
	}

	/**
	 * Método para alterar o atributo numero.
	 * 
	 * @param numero
	 */
	public void setNumero(int numero) {
		this.numero = numero;
	}

	/**
	 * Método de consulta do atributo agencia.
	 *  
	 * @return agencia
	 */
	public int getAgencia() {
		return agencia;
	}

	/**
	 * Método para alterar o atributo agencia.
	 * 
	 * @param agencia
	 */
	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}

	/**
	 * Método de consulta do atributo senha.
	 * 
	 * @return senha
	 */
	public int getSenha() {
		return senha;
	}

	/**
	 * Método para alterar o atributo senha.
	 * 
	 * @param senha
	 */
	public void setSenha(int senha) {
		this.senha = senha;
	}

	/**
	 * Método de consulta do atributo saldo.
	 * 
	 * @return
	 */
	public double getSaldo() {
		return saldo;
	}

	/**
	 * Método para o tratamento do valor de saque.
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
	 * Método para o tratamento do valor de depósito.
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
	 * Método para o adiciona uma nova
	 * movimentação no fluxo de visualização do extrato.
	 * 
	 * @param m
	 */
	public void addMovimentacao(Movimentacao m){
		this.movimentacoes.add(m);
	}



	/**
	 * Método para visualização de extrato na tela.
	 * 
	 * @throws IOException
	 */
	public void emiteExtratoTela() throws IOException{

		/*
		 *  Chamada do método estático UtilProperties.getProp() para implementar 
		 *  mensagens parametrizadas em arquivo ".properties"
		 */ 
		Properties prop = UtilProperties.getProp();

		/*
		 *  Classe SimpleDateFormat implementada para formatar a data/hora.
		 */
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

		/*
		 *  Implementado a função ArrayList (List), com finalidade de exibir todos arranjo
		 *  de movimentações realizadas pelo cliente.
		 */
		for (Movimentacao m : this.movimentacoes) {
			System.out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		System.out.println(prop.getProperty("prop.conta.layout.barra"));
		System.out.printf("%-40s%20.2f \n",
				prop.getProperty("prop.conta.cabecalho.saldoFinal"),this.saldo);	
	}

	/**
	 * Método para emissão de extrato em arquivo do tipo ".txt"
	 * 
	 * @throws IOException
	 */
	public void emiteExtratoArquivo() throws IOException{

		/*
		 *   Chamada do método estático UtilProperties.getProp() para implementar 
		 *  mensagens parametrizadas em arquivo ".properties"
		 */ 
		Properties prop = UtilProperties.getProp();

		/*
		 *  Classe SimpleDateFormat implementada para formatar a data/hora.
		 */
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");	
		SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date d = new Date();

		/**
		 * Classe FileWriter implementada para inserir fluxos de caracteres
		 * em arquivos, exemplo ".txt".
		 */
		
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
		
		/*
		 *  Implementado a função ArrayList (List), com finalidade de exibir todos arranjo
		 *  de movimentações realizadas pelo cliente.
		 */
		for (Movimentacao m : this.movimentacoes) {
			out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		out.println(prop.getProperty("prop.conta.layout.barra"));	
		out.printf("%-40s%20.2f \n\n",
				prop.getProperty("prop.conta.cabecalho.saldoFinal"),1000.004);		

		out.close();

	}

}
