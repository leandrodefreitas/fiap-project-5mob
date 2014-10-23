package br.com.fiap.financas.common.vo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import android.util.Log;

public class RegistroVO {
	
	public static enum Tipo {
		GANHO, GASTO;
	}
	
	private int codigo;
	private Tipo tipo;
	private String descricao;
	private double valor;
	private Date data;
	private ArrayList<CategoriaVO> categorias;	
	private int parcela;
	private int numParcelas;
	private String local;
	private String foto;
	
	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	
	public void tipo( int opcao )
	{
		Log.i( "", "Despesas:----------------------- " + opcao ) ;
		switch (opcao)
		{
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
	
	
	public double getValor() {
		return valor;
	}
	
	public void setValor(double valor) {
		this.valor = valor;
	}
	
	
	public Date getData() {
		return data;
	}
	
	public void setData(Date data) {
		this.data = data;
	}
	
	public String getDataFormatted( )
	{
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" ) ;
		return sdf.format( data ) ;
	}

	public void setData( String data )
	{
		SimpleDateFormat sdf = new SimpleDateFormat( "dd/MM/yyyy" ) ;
		try
		{
			this.data = sdf.parse( data ) ;
		}
		catch (Exception e)
		{
			Log.e( "erro", e.getMessage( ) ) ;
		}
	}	
	
	public ArrayList<CategoriaVO> getCategorias() {
		return categorias;
	}

	public void setCategorias(ArrayList<CategoriaVO> categorias) {
		this.categorias = categorias;
	}

	public int getParcela() {
		return parcela;
	}
	
	public void setParcela(int parcela) {
		this.parcela = parcela;
	}
	
	
	public int getNumParcelas() {
		return numParcelas;
	}
	
	public void setNumParcelas(int numParcelas) {
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
