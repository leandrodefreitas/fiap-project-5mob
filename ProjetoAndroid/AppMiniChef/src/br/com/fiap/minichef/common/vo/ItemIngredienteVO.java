package br.com.fiap.minichef.common.vo;

import java.io.Serializable;

public class ItemIngredienteVO implements Serializable {
	
	private static final long serialVersionUID = -6188973525980065778L;
	
	private Integer id;
	private Integer idReceita;
	private Integer idIngrediente;
	private Integer tipo;
	private Double quantidade;
	private String unidadeMedida;
	
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getTipo() {
		return tipo;
	}

	public void setTipo(Integer tipo) {
		this.tipo = tipo;
	}

	public Integer getIdReceita() {
		return idReceita;
	}

	public void setIdReceita(Integer idReceita) {
		this.idReceita = idReceita;
	}

	public Integer getIdIngrediente() {
		return idIngrediente;
	}

	public void setIdIngrediente(Integer idIngrediente) {
		this.idIngrediente = idIngrediente;
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
