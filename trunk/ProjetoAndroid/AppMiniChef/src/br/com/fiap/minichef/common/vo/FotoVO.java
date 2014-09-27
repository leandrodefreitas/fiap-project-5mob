package br.com.fiap.minichef.common.vo;

import java.io.Serializable ;

public class FotoVO implements Serializable
{
	public static final String ENVIADA     = "E" ;
	public static final String NAO_ENVIADA = "N" ;
	
	private String matricula ;
	private String id ;
	private String status ;
	private String caminho ;
	private int sequencia ;
	
	public FotoVO( )
	{
		matricula = "" ;
		id        = "" ;
		status    = FotoVO.NAO_ENVIADA ;
		caminho   = "" ;
		sequencia = 100 ;
	}
	
	public FotoVO( String matricula, String id, String status, String caminho, int sequencia )
	{
		this.matricula = matricula ;
		this.id = id ;
		this.status = status ;
		this.caminho = caminho ;
		this.sequencia = sequencia ;
	}
	
	public String getMatricula( )
	{
		return matricula ;
	}

	public void setMatricula( String matricula )
	{
		this.matricula = matricula ;
	}

	public int getSequencia( )
	{
		return sequencia ;
	}

	public void setSequencia( int sequencia )
	{
		this.sequencia = sequencia ;
	}

	public String getId( )
	{
		return id ;
	}
	public void setId( String id )
	{
		this.id = id ;
	}
	public String getStatus( )
	{
		return status ;
	}
	public void setStatus( String status )
	{
		this.status = status ;
	}
	public String getCaminho( )
	{
		return caminho ;
	}
	public void setCaminho( String caminho )
	{
		this.caminho = caminho ;
	}
}