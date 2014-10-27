package br.com.fiap.financas.common.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class RegistroVO {
	
	public static enum Tipo {
		GANHO, GASTO;
	}
	
	private Integer codigo;
	private Tipo tipo;
	private String descricao;
	private Double valor;
	private Date data;
	private ArrayList<CategoriaVO> categorias;	
	private Integer parcela;
	private Integer numParcelas;
	private String local;
	private String foto;
	
	public Integer getCodigo() {
		return codigo;
	}
	
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
	public void tipo(Integer opcao) {
		Log.i( "", "REGISTRO:----------------------- " + opcao ) ;
		switch (opcao) {
			case 0:
				this.tipo = Tipo.GANHO;
			case 1:
				this.tipo = Tipo.GASTO;
		}
	}	
	
	public Tipo getTipo() {
		return tipo;
	}
	
	public void setTipo(Tipo tipo) {
		this.tipo = tipo;
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
	
	public ArrayList<CategoriaVO> getCategorias() {
		return categorias;
	}

	public void setCategorias(ArrayList<CategoriaVO> categorias) {
		this.categorias = categorias;
	}

	public Integer getParcela() {
		return parcela;
	}
	
	public void setParcela(Integer parcela) {
		this.parcela = parcela;
	}
	
	
	public Integer getNumParcelas() {
		return numParcelas;
	}
	
	public void setNumParcelas(Integer numParcelas) {
		this.numParcelas = numParcelas;
	}
	
	
	public String getLocal() {
		return local;
	}
	
	public void setLocal(String local) {
		this.local = local;
	}
	
	
	public String getFoto() {
		return foto;
	}
	
	public void setFoto(String foto) {
		this.foto = foto;
	}
	
	

}
