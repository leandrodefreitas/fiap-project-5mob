package br.com.fiap.minichef.common.vo;

import java.io.Serializable;

public class IngredienteVO implements Serializable {
	
	private static final long serialVersionUID = 8306358434780595447L;
	
	private Integer id;
	private String descricao;
	private Double quantidade;
	private String unidadeMedida;
	
	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {

		return descricao;
	}

	public Double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(Double quantidade) {
		this.quantidade = quantidade;
	}

	public String getUnidadeMedida() {
		return unidadeMedida;
	}

	public void setUnidadeMedida(String unidadeMedida) {
		this.unidadeMedida = unidadeMedida;
	}

}
