package br.com.fiap.financas.common.vo;

import java.io.Serializable;

public class CategoriaVO implements Serializable {
	
	private static final long serialVersionUID = 8306358434780595447L;
	
	private Integer id;
	private String descricao;
	
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

}
