package br.com.fiap.sistema.conta;

/*
 * Inst�ncia do tipo enum.
 * Utilizada para uma lista constante dos itens CREDITO
 * e DEBITO, com o seus respectivos c�digose e descri��es.
 * 
 */
public enum Movimento {

	CREDITO("Dep�sito", 1), DEBITO("Saque   ", 2);

	private String descricao;
	private int codigo;

	/**
	 * M�todo Movimento, que altera os atributos
	 * descri��o e codigo
	 * 
	 * @param descricao
	 * @param codigo
	 */
	Movimento(String descricao, int codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	/**
	 * M�todo de consulta do atributo codigo.
	 * 
	 * @return codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * M�todo de consulta do atributo descricao.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

}
