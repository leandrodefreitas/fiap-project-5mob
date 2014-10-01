package br.com.fiap.minichef.services.scn ;

import java.util.List ;

import org.apache.log4j.Logger ;

import android.content.Context ;
import br.com.fiap.minichef.common.dao.ContatoDAO;
import br.com.fiap.minichef.common.dao.FotoDAO;
import br.com.fiap.minichef.common.vo.ContatoVO;
import br.com.fiap.minichef.common.vo.FotoVO;
import br.com.fiap.minichef.exceptions.CadastroException;

public class CadastroSCN 
{
	private Context context ;
	private final Logger log = Logger.getLogger( CadastroSCN.class ) ;
	
	public CadastroSCN( Context context )
	{
		this.context = context ;
	}
	
	public CadastroSCN(  )
	{
	}
	
	public void salvarContato( ContatoVO vo ) throws CadastroException
	{
		try
		{
			ContatoDAO dao = new ContatoDAO( context ) ;
			dao.insert( vo ) ;
		}
		catch (Exception exception)
		{
			throw new CadastroException( ) ;
		}
	}
	
	public List<ContatoVO> selectAllOrderName( ) throws CadastroException
	{
		try
		{
			ContatoDAO dao = new ContatoDAO( context ) ;
			return dao.selectAllOrderName( ) ;
		}
		catch (Exception exception)
		{
			throw new CadastroException( ) ;
		}
	}
	
	public void excluirTodasFotos( ) throws CadastroException
	{
		try
		{
			FotoDAO dao = new FotoDAO( context ) ;
			dao.deleteAll( ) ;
		}
		catch (Exception exception)
		{
			throw new CadastroException( ) ;
		}
	}
	
	public void registrarFoto( String matricula ) throws CadastroException
	{
		try
		{
			{
				FotoDAO dao = new FotoDAO( context ) ;
				List<FotoVO> fotos = dao.selectByMatricula( matricula )  ;
				log.info( "invocando webServices ::: registrarFoto ") ;
				for (int i = 0; i < fotos.size( ); i++ )
				{
					FotoVO fotoVO = ( FotoVO ) fotos.get( i ) ;
					{
						fotoVO.setStatus( FotoVO.ENVIADA ) ;
						dao.update( fotoVO ) ;
						log.info( "metodo registrarFoto ::: executado ::: true"  ) ;
					}
				}
			}
		}
		catch (Exception exception)
		{
			throw new CadastroException( ) ;
		}
	}
}