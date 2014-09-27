package br.com.fiap.minichef.common.vo ;

public class AcumuladosVO
{

	private String mega_da_virada ;

	public String getMega_da_virada( )
	{
		return mega_da_virada ;
	}

	public void setMega_da_virada( String mega_da_virada )
	{
		this.mega_da_virada = mega_da_virada ;
	}

	class Especial
	{
		private String proximo ;
		private String final_proximo ;
		private String acumulado ;

		public String getProximo( )
		{
			return proximo ;
		}

		public void setProximo( String proximo )
		{
			this.proximo = proximo ;
		}

		public String getFinal_proximo( )
		{
			return final_proximo ;
		}

		public void setFinal_proximo( String final_proximo )
		{
			this.final_proximo = final_proximo ;
		}

		public String getAcumulado( )
		{
			return acumulado ;
		}

		public void setAcumulado( String acumulado )
		{
			this.acumulado = acumulado ;
		}

	}

}
