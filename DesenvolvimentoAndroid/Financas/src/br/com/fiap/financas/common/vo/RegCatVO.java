package br.com.fiap.financas.common.vo;

import java.io.Serializable;

public class RegCatVO implements Serializable {
	
	private static final long serialVersionUID = -6188973525980065778L;
	
	private Integer id;
	private Integer idRegistro;
	private Integer idCategoria;
	private Integer tipo;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getIdRegistro() {
		return idRegistro;
	}
	
	public void setIdRegistro(Integer idRegistro) {
		this.idRegistro = idRegistro;
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
