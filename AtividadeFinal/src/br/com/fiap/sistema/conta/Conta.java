package br.com.fiap.sistema.conta;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import br.com.fiap.sistema.exceptions.SaldoInsuficienteException;
import br.com.fiap.sistema.exceptions.ValorInvalidoException;


public abstract class Conta{

	protected double saldo = 10000;
	private int numero;
	private int agencia;
	private int senha;
	private String nome;
	private ArrayList<Movimentacao> movimentacoes = new ArrayList<Movimentacao>();	

	public Conta(int numero, int agencia, int senha, String nome) {
		super();
		this.numero = numero;
		this.agencia = agencia;
		this.senha = senha;
		this.nome = nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getAgencia() {
		return agencia;
	}

	public void setAgencia(int agencia) {
		this.agencia = agencia;
	}
	
	public int getSenha() {
		return senha;
	}

	public void setSenha(int senha) {
		this.senha = senha;
	}

	public double getSaldo() {
		return saldo;
	}

	public void saca(double valor) {
		if (valor > this.saldo) {
			throw new SaldoInsuficienteException(valor);
		} else {
			this.saldo -= valor;
			Movimentacao m = new Movimentacao(new Date(), valor, this.saldo, Movimento.DEBITO);
			addMovimentacao(m);
		}
	}
	
	public void deposita(double valor) throws ValorInvalidoException {
		if (valor < 0) {
			throw new ValorInvalidoException(valor);
		} else {
			this.saldo += valor;
			Movimentacao m = new Movimentacao(new Date(), valor, this.saldo, Movimento.CREDITO);
			addMovimentacao(m);
		}
	}
	
	public void addMovimentacao(Movimentacao m){
		this.movimentacoes.add(m);
	}
	
	public void emiteExtratoTela(){
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");	
		SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date d = new Date();
		
		// cabeçalho
		System.out.printf("\n%45s\n","Sistema de Auto-Atendimento");
		System.out.printf("%-39s%21s\n","Extrato de Conta Corrente",formatador2.format(d));
		System.out.printf("%-20s%-20s%.20s \n","Agência: "+ this.agencia,"Conta: " + this.numero, "Cliente: " + this.nome);	
		System.out.printf("%-15s%-17s%-14s%14s\n","Data","Descrição","Valor","Saldo");
		System.out.println("____________________________________________________________");
		// imprime movimentações
		for (Movimentacao m : this.movimentacoes) {
			System.out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		System.out.println("____________________________________________________________");
		System.out.printf("%-40s%20.2f \n","Saldo Final:",this.saldo);	
	}
	
	public void emiteExtratoArquivo() throws IOException{
		
		SimpleDateFormat formatador = new SimpleDateFormat("dd/MM/yyyy");	
		SimpleDateFormat formatador2 = new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss");
		Date d = new Date();
				
		FileWriter fw = new FileWriter("extrato.txt");
		PrintWriter out = new PrintWriter(fw);

		out.printf("%45s \n","Sistema de Auto-Atendimento");
		out.printf("%-39s%21s \n","Extrato de Conta Corrente",formatador2.format(d));
		out.printf("%-20s%-20s%.20s \n","Agência: "+ this.agencia,"Conta: " + this.numero, "Cliente: " + this.nome);			
		out.printf("%-15s%-17s%-14s%14s\n","Data","Descrição","Valor","Saldo");
		out.println("____________________________________________________________");			
		for (Movimentacao m : this.movimentacoes) {
			out.printf("%-15s%-17s%-14.2f%14.2f \n",formatador.format(m.getData()), m.getMovimento().getDescricao(), m.getValor(), m.getSaldo());
		}
		out.println("____________________________________________________________");	
		out.printf("%-40s%20.2f \n\n","Saldo Final",1000.004);		

		out.close();

	}

}
