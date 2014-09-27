package br.com.fiap.minichef.common.vo ;

public class MegasenaVO
{

	private ConcursoVO concurso ;
	private SorteioVO sorteio ;
	private RateioVO rateio ;
	private ProximoVO proximo ;
	private AcumuladosVO acumulados ;
	private GanhadoresVO ganhadores ;

	public ConcursoVO getConcurso( )
	{
		return concurso ;
	}

	public void setConcurso( ConcursoVO concurso )
	{
		this.concurso = concurso ;
	}

	public SorteioVO getSorteio( )
	{
		return sorteio ;
	}

	public void setSorteio( SorteioVO sorteio )
	{
		this.sorteio = sorteio ;
	}

	public RateioVO getRateio( )
	{
		return rateio ;
	}

	public void setRateio( RateioVO rateio )
	{
		this.rateio = rateio ;
	}

	public ProximoVO getProximo( )
	{
		return proximo ;
	}

	public void setProximo( ProximoVO proximo )
	{
		this.proximo = proximo ;
	}

	public AcumuladosVO getAcumulados( )
	{
		return acumulados ;
	}

	public void setAcumulados( AcumuladosVO acumulados )
	{
		this.acumulados = acumulados ;
	}

	public GanhadoresVO getGanhadores( )
	{
		return ganhadores ;
	}

	public void setGanhadores( GanhadoresVO ganhadores )
	{
		this.ganhadores = ganhadores ;
	}

}
