package br.com.fiap.financas.common.vo;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.util.Log;
import br.com.fiap.financas.util.Util;

public class GanhoVO {
	
	public final static int GANHO = 1;
	
	private Integer id;
	private String descricao;
	private Double valor;
	private Date data;
	private CategoriaVO categoria;
	private Integer parcela;
	private Integer numParcelas;
	private Double saldo;

	
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
	
	public CategoriaVO getCategoria() {
		return categoria;
	}

	public void setCategoria(CategoriaVO categoria) {
		this.categoria = categoria;
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
	
	public Double getSaldo() {
		return saldo;
	}

	public void setSaldo(Double saldo) {
		this.saldo = saldo;
	}

	@Override
	public String toString() {

		return this.getDescricao() + " de " + Util.imprimeDataFormatoBR(this.getDataFormatted());
	}

}
