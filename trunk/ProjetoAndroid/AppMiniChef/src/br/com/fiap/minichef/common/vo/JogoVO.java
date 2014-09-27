package br.com.fiap.minichef.common.vo ;

import java.io.Serializable ;

public class JogoVO implements Serializable
{
	private int id ;
	private String nome ;
	private String numeros ;

	public int getId( )
	{
		return id ;
	}

	public void setId( int id )
	{
		this.id = id ;
	}

	public String getNome( )
	{
		return nome ;
	}

	public void setNome( String nome )
	{
		this.nome = nome ;
	}

	public String getNumeros( )
	{
		return numeros ;
	}

	public void setNumeros( String numeros )
	{
		this.numeros = numeros ;
	}

	@Override
	public String toString( )
	{
		return this.getNumeros( ).replace( "|", " " ) + " - " + this.getNome( ) ;
	}

}
