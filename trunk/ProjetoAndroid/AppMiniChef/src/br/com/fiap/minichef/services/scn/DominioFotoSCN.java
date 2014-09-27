package br.com.fiap.minichef.services.scn ;

import java.util.List ;

import org.apache.log4j.Logger ;

import android.content.Context ;
import br.com.fiap.minichef.common.dao.FotoDAO;
import br.com.fiap.minichef.common.vo.FotoVO;
import br.com.fiap.minichef.exceptions.DominioException;

public class DominioFotoSCN
{
	private Context context ;
	private final Logger log = Logger.getLogger( DominioFotoSCN.class ) ;

	public DominioFotoSCN( Context context )
	{
		this.context = context ;
	}
	
	public List<FotoVO> listar( ) throws DominioException
	{
		List<FotoVO> lista = null ;
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			lista = dao.selectAll( ) ;
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
		return lista ;
	}
	
	public long inserirFoto( FotoVO vo ) throws DominioException
	{
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			return ( dao.insert( vo ) ) ;
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
	}
	
	public int getSequencia( String matricula, String vistoria ) throws DominioException
	{
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			return ( dao.getMaxSequence( matricula, vistoria ) ) ;
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
	}
	
	public void alterarFoto( FotoVO vo ) throws DominioException
	{
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			dao.update( vo )  ;
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
	}
	
	public int getTotalFotoByVistoria( int vistoria ) throws DominioException
	{
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			return ( dao.getCountByVistoria( vistoria ) ) ;
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
	}
	
	public List<FotoVO> selectByVistoria( String vistoria ) throws DominioException
	{
		List<FotoVO> lista = null ;
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			lista = dao.selectByVistoria( vistoria );
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
		return lista ;
	}
	
	public List<FotoVO> selectAllSended( ) throws DominioException
	{
		List<FotoVO> lista = null ;
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			lista = dao.selectAllSended( FotoVO.ENVIADA );
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
		return lista ;
	}
	
	public int deleteByVistoriaAndSequencia( String vistoria, int sequencia ) throws DominioException
	{
		int retorno;
		FotoDAO dao = new FotoDAO( context ) ;
		try
		{
			retorno = dao.deleteByVistoriaAndSequencia( vistoria, sequencia );
		}
		catch (Exception exception)
		{
			log.error( exception ) ;
			throw new DominioException( ) ;
		}
		finally
		{
			dao.close( ) ;
		}
		return retorno ;
	}
}