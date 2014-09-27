package br.com.fiap.minichef.common.vo ;

public class ConcursoVO
{

	private String numero_sorteio ;
	private String premio_acumulado ;
	private String data ;
	private String local ;
	private String total_arrecadado ;

	public String getNumero_sorteio( )
	{
		return numero_sorteio ;
	}

	public void setNumero_sorteio( String numero_sorteio )
	{
		this.numero_sorteio = numero_sorteio ;
	}

	public String getPremio_acumulado( )
	{
		return premio_acumulado ;
	}

	public void setPremio_acumulado( String premio_acumulado )
	{
		this.premio_acumulado = premio_acumulado ;
	}

	public String getData( )
	{
		return data ;
	}

	public void setData( String data )
	{
		this.data = data ;
	}

	public String getLocal( )
	{
		return local ;
	}

	public void setLocal( String local )
	{
		this.local = local ;
	}

	public String getTotal_arrecadado( )
	{
		return total_arrecadado ;
	}

	public void setTotal_arrecadado( String total_arrecadado )
	{
		this.total_arrecadado = total_arrecadado ;
	}

}
