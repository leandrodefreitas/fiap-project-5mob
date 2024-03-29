package br.com.fiap.minichef.common.vo ;

import java.io.Serializable ;

public class ContatoVO implements Serializable
{
	private int codigo ;
	private String nome ;
	private String endereco ;
	private String telefone ;
	
	public ContatoVO( )
	{
		this.codigo   = 0 ;
		this.endereco = null ;
		this.telefone = null ;
	}

	public int getCodigo( )
	{
		return codigo ;
	}

	public void setCodigo( int codigo )
	{
		this.codigo = codigo ;
	}

	public String getNome( )
	{
		return nome ;
	}

	public void setNome( String nome )
	{
		this.nome = nome ;
	}

	public String getEndereco( )
	{
		return endereco ;
	}

	public void setEndereco( String endereco )
	{
		this.endereco = endereco ;
	}
	
	public String getTelefone( )
	{
		return telefone ;
	}

	public void setTelefone( String telefone )
	{
		this.telefone = telefone ;
	}

	public String toString( )
	{
		return " codigo :" + codigo + " nome: " + nome + " endereco: " + endereco + " telefone: " + telefone  ;
	}
}