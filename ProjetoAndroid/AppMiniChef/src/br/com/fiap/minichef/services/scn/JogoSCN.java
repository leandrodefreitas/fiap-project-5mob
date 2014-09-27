package br.com.fiap.minichef.services.scn ;

import java.util.List ;

import android.content.Context ;
import br.com.fiap.minichef.common.dao.JogoDAO;
import br.com.fiap.minichef.common.vo.JogoVO;

public class JogoSCN
{

	private Context context ;

	public JogoSCN( Context context )
	{
		this.context = context ;
	}

	public void salvarJogo( JogoVO jogo )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		dao.insert( jogo ) ;
		dao.close( ) ;
	}

	public JogoVO obterJogoPorNome( String nome )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		JogoVO jogo = dao.selecionarPorNome( nome ) ;
		dao.close( ) ;
		return jogo ;
	}

	public JogoVO obterJogoPorNumeros( String numeros )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		JogoVO jogo = dao.selecionarPorNumeros( numeros ) ;
		dao.close( ) ;
		return jogo ;
	}

	public List<JogoVO> obterTodosJogos( )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		List<JogoVO> jogos = dao.selectAll( ) ;
		dao.close( ) ;
		return jogos ;
	}

	public void excluirJogoPorId( int id )
	{
		JogoDAO dao = new JogoDAO( context ) ;
		dao.excluirPorId( id ) ;
		dao.close( ) ;
		return ;
	}

}
