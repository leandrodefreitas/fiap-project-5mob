package br.com.fiap.minichef.common.vo ;

import java.util.List ;

public class GanhadoresVO
{

	private String por_estado ;
	private List<Por_cidade> por_cidade ;

	public String getPor_estado( )
	{
		return por_estado ;
	}

	public void setPor_estado( String por_estado )
	{
		this.por_estado = por_estado ;
	}

	public List<Por_cidade> getPor_cidade( )
	{
		return por_cidade ;
	}

	public void setPor_cidade( List<Por_cidade> por_cidade )
	{
		this.por_cidade = por_cidade ;
	}

	class Por_cidade
	{
		private String num_ganhadores ;
		private String cidade ;

		public String getNum_ganhadores( )
		{
			return num_ganhadores ;
		}

		public void setNum_ganhadores( String num_ganhadores )
		{
			this.num_ganhadores = num_ganhadores ;
		}

		public String getCidade( )
		{
			return cidade ;
		}

		public void setCidade( String cidade )
		{
			this.cidade = cidade ;
		}

	}
}
