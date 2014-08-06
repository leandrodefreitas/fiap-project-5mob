package br.com.fiap.sistema.conta;

public enum Movimento {

	CREDITO("Depósito", 1), DEBITO("Saque   ", 2);

	private String descricao;
	private int codigo;

	Movimento(String descricao, int codigo) {
		this.descricao = descricao;
		this.codigo = codigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public String getDescricao() {
		return descricao;
	}

}
