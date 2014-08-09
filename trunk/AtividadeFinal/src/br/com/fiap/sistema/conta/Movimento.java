package br.com.fiap.sistema.conta;

/*
 * Instância do tipo enum.
 * Utilizada para uma lista constante dos itens CREDITO
 * e DEBITO, com o seus respectivos códigose e descrições.
 * 
 */
public enum Movimento {

	CREDITO("Depósito", 1), DEBITO("Saque   ", 2);

	private String descricao;
	private int codigo;

	/**
	 * Método Movimento, que altera os atributos
	 * descrição e codigo
	 * 
	 * @param descricao
	 * @param codigo
	 */
	Movimento(String descricao, int codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	/**
	 * Método de consulta do atributo codigo.
	 * 
	 * @return codigo
	 */
	public int getCodigo() {
		return codigo;
	}

	/**
	 * Método de consulta do atributo descricao.
	 * 
	 * @return descricao
	 */
	public String getDescricao() {
		return descricao;
	}

}
