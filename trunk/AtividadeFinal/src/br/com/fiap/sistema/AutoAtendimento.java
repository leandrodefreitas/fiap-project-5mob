package br.com.fiap.sistema;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import br.com.fiap.sistema.conta.ContaCorrente;
import br.com.fiap.sistema.exceptions.SaldoInsuficienteException;
import br.com.fiap.sistema.exceptions.ValorInvalidoException;

public class AutoAtendimento {

	public static void main(String[] args) {
		
		boolean continuar = true;
		double valor = 0;
		
		try {
			
			Scanner s  = new Scanner(System.in);			
			System.out.println("Bem vindo ao sistema de Auto-Atendimento");
			System.out.println("Criando uma nova conta..");
			
			System.out.print("\nInforme seu nome:");
			String nome = s.next();

			System.out.print("Agência:");
			int agencia = s.nextInt();
			
			System.out.print("Conta:");
			int numero = s.nextInt();
			
			System.out.print("Senha:");			
			int senha = s.nextInt();
			
			ContaCorrente conta = new ContaCorrente(numero, agencia, senha, nome);
			
			System.out.println("\nOlá, "+ conta.getNome()+". Você foi premiado com um saldo de R$ 10.000,00 na sua conta!");

			do {
				
				System.out.println("\nSelecione a operação desejada: \n 1: Saque \n 2: Depósito "
						+ "\n 3: Consulta de Saldo  \n 4: Consulta de Extrato \n 5: Cálculo de Imposto \n 0: Sair");
				System.out.print("Operação: ");
				int operacao = s.nextInt();
					
				try {
					switch(operacao){
					case 1:
						System.out.print("\nInforme o valor do saque: ");
						valor = s.nextDouble();
						conta.saca(valor);
						System.out.println("Saque efetuado com sucesso!");
						break;
					case 2:
						System.out.print("\nInforme o valor do depósito: ");
						valor = s.nextDouble();
						conta.deposita(valor);
						System.out.println("Depósito efetuado com sucesso!");
						break;
					case 3:
						System.out.printf("\nSaldo: R$ %.2f \n", conta.getSaldo());
						break;
					case 4:

						boolean continuar3 = true;
						do {
							System.out.println("\nEscolha a forma de impressão: \n 1: Tela \n 2: Arquivo \n 3: Cancelar");
							System.out.print("Opção: ");
							operacao = s.nextInt();
							switch (operacao) {
							case 1:
								continuar3 = false;
								conta.emiteExtratoTela();
								break;
							case 2:
								continuar3 = false;	
								System.out.println("\nGerando arquivo...");
								try {
									conta.emiteExtratoArquivo();	
									System.out.println("\nArquivo gerado com sucesso.");
								} catch (IOException e) {
									System.out.println("\nErro na geração do arquivo.");
								}
	
								break;
							case 3:
								continuar3 = false;
								System.out.println("\nConsulta cancelada.");						
								break;
							default:
								System.out.println("\nOpção inválida. Tente Novamente.");								
								break;
							}							
							
						} while (continuar3);
						break;
					case 5:
						System.out.printf("\nImposto da conta: R$ %.2f \n", conta.calculaTributos());
						break;
					case 0:
						continuar = false;
						System.out.println("\nSessão finalizada.");
						break;
					default:
						System.out.println("\nOperação inválida.");								
						break;
					}					
				} catch (ValorInvalidoException e) {
					System.out.println(e.getMessage());
				} catch (SaldoInsuficienteException e) {
					System.out.println(e.getMessage());
				}
				
				if (continuar){
					boolean continuar2 = true;					
					do {
						System.out.println("\nDeseja realizar outra operação? \n 1: Sim \n 2: Não");
						System.out.print("Opção: ");
						operacao = s.nextInt();	
					
						switch (operacao) {
						case 1:
							continuar2 = false;						
							break;
						case 2:
							continuar = false;
							continuar2 = false;
							System.out.println("\nSessão finalizada.");						
							break;
						default:
							System.out.println("\nOpção inválida. Tente Novamente.");								
							break;
						}
					} while (continuar2);						
				}
			
			} while (continuar);
			
			s.close();
			
		} catch (InputMismatchException e) {
			System.out.println("\nErro de digitação. Tente novamente.");
			main(args);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("\nErro no sistema. Tente novamente.");
			main(args);
		}
		
	}

}
