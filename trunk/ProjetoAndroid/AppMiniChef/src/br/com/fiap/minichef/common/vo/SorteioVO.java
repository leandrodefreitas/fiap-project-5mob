package br.com.fiap.minichef.common.vo ;

import java.util.List ;

public class SorteioVO
{

	private List<String> ordem_sorteio ;
	private List<String> ordem_crescente ;

	public List<String> getOrdem_sorteio( )
	{
		return ordem_sorteio ;
	}

	public void setOrdem_sorteio( List<String> ordem_sorteio )
	{
		this.ordem_sorteio = ordem_sorteio ;
	}

	public List<String> getOrdem_crescente( )
	{
		return ordem_crescente ;
	}

	public void setOrdem_crescente( List<String> ordem_crescente )
	{
		this.ordem_crescente = ordem_crescente ;
	}

}
