package br.com.fiap.minichef.exceptions ;


public class DominioException extends Exception
{
	private String message ;

	public String getMessage( )
	{
		return message ;
	}

	public void setMessage( String message )
	{
		this.message = message ;
	}

}
