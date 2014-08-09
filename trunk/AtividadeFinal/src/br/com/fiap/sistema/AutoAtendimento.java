package br.com.fiap.sistema;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Properties;
import java.util.Scanner;

import br.com.fiap.sistema.conta.ContaCorrente;
import br.com.fiap.sistema.exceptions.SaldoInsuficienteException;
import br.com.fiap.sistema.exceptions.ValorInvalidoException;
import br.com.fiap.sistema.util.UtilProperties;

public class AutoAtendimento {

	/**
	 * M�todo main � respons�vel por inicializar a aplica��o
	 * 
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException {

		/*
		 *   Chamada do m�todo est�tico UtilProperties.getProp() para implementar 
		 *  mensagens parametrizadas em arquivo ".properties"
		 */ 
		Properties prop = UtilProperties.getProp();

		boolean continuar = true;
		double valor = 0;

		try {

			/*
			 *  Implementa��o da classe Scanner, que converte textos para tipos primitivos, 
			 *  sendo que esses textos podem ser considerados como objetos do tipo String, 
			 *  InputStream e arquivos.
			 */
			Scanner s  = new Scanner(System.in);			
			System.out.println(prop.getProperty("prop.atendimento.bemVindo"));
			System.out.println(prop.getProperty("prop.atendimento.criandoNovaConta"));

			System.out.print(prop.getProperty("prop.atendimento.informeSeuNome"));
			String nome = s.next();

			System.out.print(prop.getProperty("prop.atendimento.agencia"));
			int agencia = s.nextInt();

			System.out.print(prop.getProperty("prop.atendimento.conta"));
			int numero = s.nextInt();

			System.out.print(prop.getProperty("prop.atendimento.senha"));			
			int senha = s.nextInt();

			/*
			 *  Chamada da Classe ContaCorrente, que extende a classe Conta 
			 *  que implementa a interface Tributavel
			 */
			ContaCorrente conta = new ContaCorrente(numero, agencia, senha, nome);

			System.out.println(prop.getProperty("prop.atendimento.ola") + conta.getNome()+
					prop.getProperty("prop.atendimento.premiadoSaldoConta"));

			do {

				System.out.println(
						prop.getProperty("prop.atendimento.selecioneOperacao") +
						prop.getProperty("prop.atendimento.saque") + 
						prop.getProperty("prop.atendimento.deposito") + 
						prop.getProperty("prop.atendimento.consultaSaldo") + 
						prop.getProperty("prop.atendimento.consultaExtrato") + 
						prop.getProperty("prop.atendimento.calculoImposto") + 
						prop.getProperty("prop.atendimento.sair")
						);
				System.out.print(prop.getProperty("prop.atendimento.operacao"));
				int operacao = s.nextInt();

				/*
				 * Utilizado a fun��o switch/case, que verifica a vari�vel 
				 * enviada, e direciona para a op��o correspondente.
				 * 
				 * Para esta opera��o foi utilizada as op��es de 1 � 6 ( 
				 * 1: Saque, 2: Dep�sito, 3: Consulta de Saldo, 
				 * 4: Consulta de Extrato, 5: C�lculo de Imposto, 0: Sair),
				 * sendo a op��o default, a de tratamento de erro (op��o inv�lida).
				 */
				try {
					switch(operacao){
					case 1:
						System.out.print(prop.getProperty("prop.atendimento.informeValorSaque"));
						valor = s.nextDouble();
						conta.saca(valor);
						System.out.println(prop.getProperty("prop.atendimento.saqueEfetuado"));
						break;
					case 2:
						System.out.print((prop.getProperty("prop.atendimento.informeValorDeposito")));
						valor = s.nextDouble();
						conta.deposita(valor);
						System.out.println(prop.getProperty("prop.atendimento.depositoEfetuado"));
						break;
					case 3:
						System.out.printf(prop.getProperty("prop.atendimento.saldoReais"), conta.getSaldo());
						break;
					case 4:

						boolean continuar3 = true;
						do {
							System.out.println(
									prop.getProperty("prop.atendimento.escolhaFormaImpressao") + 
									prop.getProperty("prop.atendimento.tela") + 
									prop.getProperty("prop.atendimento.arquivo") + 
									prop.getProperty("prop.atendimento.cancelar")
									);
							System.out.print(prop.getProperty("prop.atendimento.opcao"));
							operacao = s.nextInt();

							/*
							 * Utilizado a fun��o switch/case, para selecionar o 
							 * modo de visualiza��o do extrato.	 
							 *   
							 * Para esta opera��o foi utilizada as op��es de 1 � 3 ( 
							 * 1: Tela, 2: Arquivo, 3: Cancelar)
							 * sendo a op��o default, a de tratamento de erro (op��o inv�lida).
							 */
							switch (operacao) {
							case 1:
								continuar3 = false;
								conta.emiteExtratoTela();
								break;
							case 2:
								continuar3 = false;	
								System.out.println(prop.getProperty("prop.atendimento.gerandoArquivo"));
								try {
									conta.emiteExtratoArquivo();	
									System.out.println(prop.getProperty("prop.atendimento.geradoSucesso"));
								} catch (IOException e) {
									System.out.println(prop.getProperty("prop.atendimento.erroGeracaoArquivo"));
								}

								break;
							case 3:
								continuar3 = false;
								System.out.println(prop.getProperty("prop.atendimento.consultaCancelada"));						
								break;
							default:
								// Mensagem de erro para valor de op��o inv�lido.
								System.out.println(prop.getProperty("prop.atendimento.opcaoInvalida"));								
								break;
							}							

						} while (continuar3);
						break;
					case 5:
						System.out.printf(prop.getProperty("prop.atendimento.impostoConta"), conta.calculaTributos());
						break;
					case 0:
						continuar = false;
						System.out.println(prop.getProperty("prop.atendimento.sessaoFinalizada"));
						break;
					default:
						// Mensagem de erro para valor de op��o inv�lido.
						System.out.println(prop.getProperty("prop.atendimento.operacaoInvalida"));								
						break;
					}					
				} catch (ValorInvalidoException e) {
					// Mensagem de erro para envio de valores inv�lidos.
					System.out.println(e.getMessage());
				} catch (SaldoInsuficienteException e) {
					// Mensagem de erro para saldo insuficiente na conta.
					System.out.println(e.getMessage());
				}

				if (continuar){
					boolean continuar2 = true;					
					do {
						System.out.println(
								prop.getProperty("prop.atendimento.desejaRealizaOutraOperacao") + 
								prop.getProperty("prop.atendimento.sim") + 
								prop.getProperty("prop.atendimento.nao")
								);
						System.out.print(prop.getProperty("prop.atendimento.opcao"));
						operacao = s.nextInt();

						/*
						 * Utilizado a fun��o switch/case, para selecionar 
						 * o cancelamento da visualiza��o do extrato.	 
						 *   
						 * Para esta opera��o foi utilizada as op��es de 1 � 3 ( 
						 * 1: Tela, 2: Arquivo, 3: Cancelar)
						 * sendo a op��o default, a de tratamento de erro (op��o inv�lida).
						 */
						switch (operacao) {
						case 1:
							continuar2 = false;						
							break;
						case 2:
							continuar = false;
							continuar2 = false;
							System.out.println(prop.getProperty("prop.atendimento.sessaoFinalizada"));						
							break;
						default:
							// Mensagem de erro para valor de op��o inv�lido.
							System.out.println(prop.getProperty("prop.atendimento.operacaoInvalida"));								
							break;
						}
					} while (continuar2);						
				}

			} while (continuar);

			s.close();

		} catch (InputMismatchException e) {
			// Mensagem de erro para digita��o de valores inv�lidos.
			System.out.println(prop.getProperty("prop.atendimento.erroDigitacao"));
			main(args);
		} catch (Exception e) {
			e.printStackTrace();
			// Mensagem de erro de sistema.
			System.out.println(prop.getProperty("prop.atendimento.erroSistema"));
			main(args);
		}

	}

}
