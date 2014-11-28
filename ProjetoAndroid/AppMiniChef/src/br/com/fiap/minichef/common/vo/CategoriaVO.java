package br.com.fiap.minichef.common.vo;

import java.io.Serializable;

public class CategoriaVO implements Serializable {
	
	private static final long serialVersionUID = 4068063927852787450L;
	
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
