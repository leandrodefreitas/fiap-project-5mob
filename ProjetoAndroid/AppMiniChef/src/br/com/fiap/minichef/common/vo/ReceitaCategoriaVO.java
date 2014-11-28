package br.com.fiap.minichef.common.vo;

import java.io.Serializable;

public class ReceitaCategoriaVO implements Serializable {
	
	private static final long serialVersionUID = 2500805767124690779L;
	
	private Integer id;
	private Integer idReceita;
	private Integer idCategoria;
	private Integer tipo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdReceita() {
		return idReceita;
	}
	
	public void setIdReceita(Integer idReceita) {
		this.idReceita = idReceita;
	}
	
	public Integer getIdCategoria() {
		return idCategoria;
	}
	
	public void setIdCategoria(Integer idCategoria) {
		this.idCategoria = idCategoria;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}
	
}
