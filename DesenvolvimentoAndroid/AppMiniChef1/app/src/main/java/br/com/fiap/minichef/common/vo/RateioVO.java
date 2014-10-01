package br.com.fiap.minichef.common.vo ;

public class RateioVO
{

	private Sena sena ;
	private Quina quina ;
	private Quadra quadra ;

	public Sena getSena( )
	{
		return sena ;
	}

	public void setSena( Sena sena )
	{
		this.sena = sena ;
	}

	public Quina getQuina( )
	{
		return quina ;
	}

	public void setQuina( Quina quina )
	{
		this.quina = quina ;
	}

	public Quadra getQuadra( )
	{
		return quadra ;
	}

	public void setQuadra( Quadra quadra )
	{
		this.quadra = quadra ;
	}

	class Sena
	{
		private String ganhadores ;
		private String premio ;

		public String getGanhadores( )
		{
			return ganhadores ;
		}

		public void setGanhadores( String ganhadores )
		{
			this.ganhadores = ganhadores ;
		}

		public String getPremio( )
		{
			return premio ;
		}

		public void setPremio( String premio )
		{
			this.premio = premio ;
		}

	}

	class Quina
	{
		private String ganhadores ;
		private String premio ;

		public String getGanhadores( )
		{
			return ganhadores ;
		}

		public void setGanhadores( String ganhadores )
		{
			this.ganhadores = ganhadores ;
		}

		public String getPremio( )
		{
			return premio ;
		}

		public void setPremio( String premio )
		{
			this.premio = premio ;
		}

	}

	class Quadra
	{
		private String ganhadores ;
		private String premio ;

		public String getGanhadores( )
		{
			return ganhadores ;
		}

		public void setGanhadores( String ganhadores )
		{
			this.ganhadores = ganhadores ;
		}

		public String getPremio( )
		{
			return premio ;
		}

		public void setPremio( String premio )
		{
			this.premio = premio ;
		}

	}

}
