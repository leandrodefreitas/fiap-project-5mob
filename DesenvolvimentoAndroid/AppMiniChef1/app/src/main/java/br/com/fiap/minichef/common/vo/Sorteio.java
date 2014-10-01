package br.com.fiap.minichef.common.vo ;

import java.io.Serializable ;
import java.util.Date ;

public class Sorteio implements Serializable
{
	private Integer concurso ;
	private Date data ;
	private String[ ] dezenas ;

	public Integer getConcurso( )
	{
		return concurso ;
	}

	public void setConcurso( Integer concurso )
	{
		this.concurso = concurso ;
	}

	public Date getData( )
	{
		return data ;
	}

	public void setData( Date data )
	{
		this.data = data ;
	}

	public String[ ] getDezenas( )
	{
		return dezenas ;
	}

	public void setDezenas( String[ ] dezenas )
	{
		this.dezenas = dezenas ;
	}

}
