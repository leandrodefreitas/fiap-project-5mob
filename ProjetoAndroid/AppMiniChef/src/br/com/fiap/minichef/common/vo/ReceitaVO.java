package br.com.fiap.minichef.common.vo;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import android.util.Log;

public class ReceitaVO implements Serializable {
	
	private static final long serialVersionUID = 55162102414220105L;

	public final static int RECEITA = 2;	
	
	private Integer id;
	private String nome;
	private String descricao;
	private Double valor;
	private Date data;
	private List<IngredienteVO> ingredientes;
	private Integer tempo;
	private Integer nota;
	private String categoria;
	private List<CategoriaVO> categorias;
	private String foto;
	
	
	
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
	
	
	public Double getValor() {
		return valor;
	}
	
	public void setValor(Double valor) {
		this.valor = valor;
	}
	
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatted() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(data);
	}

	public void setData(String data) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			this.data = sdf.parse(data);
		}
		catch (Exception e) {
			Log.e("erro", e.getMessage());
		}
	}	
	
	public List<IngredienteVO> getIngredientes() {
		return ingredientes;
	}

	public void setIngredientes(List<IngredienteVO> ingredientes) {
		this.ingredientes = ingredientes;
	}
	
	public String getIngredientesString() {
		String strIngredientes = "";
		for (IngredienteVO ingVO : ingredientes) {
			if (strIngredientes == "")
				strIngredientes = ingVO.getDescricao();
			else
				strIngredientes = strIngredientes + " / " +ingVO.getDescricao();				
		}
		return strIngredientes;
	}

	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public Integer getTempo() {
		return tempo;
	}

	public void setTempo(Integer tempo) {
		this.tempo = tempo;
	}

	public Integer getNota() {
		return nota;
	}

	public void setNota(Integer nota) {
		this.nota = nota;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<CategoriaVO> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<CategoriaVO> categorias) {
		this.categorias = categorias;
	}
	
	

}
