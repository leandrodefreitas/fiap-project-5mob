package br.com.fiap.minichef.exceptions ;


public class CadastroException extends Exception
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
