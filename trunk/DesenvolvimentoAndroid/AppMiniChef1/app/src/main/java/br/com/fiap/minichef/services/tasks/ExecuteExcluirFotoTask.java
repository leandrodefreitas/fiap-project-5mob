package br.com.fiap.minichef.services.tasks ;


import java.io.File ;
import java.util.List ;

import org.apache.log4j.Logger ;

import android.os.AsyncTask ;
import br.com.fiap.minichef.activity.AgendaActivity;
import br.com.fiap.minichef.common.vo.FotoVO;
import br.com.fiap.minichef.services.scn.DominioFotoSCN;

public class ExecuteExcluirFotoTask extends AsyncTask<Void, Void, Void>
{
	private final Logger log = Logger.getLogger( ExecuteExcluirFotoTask.class ) ;
	
	protected void onProgressUpdate( final Void unsed )
	{
	}

	protected void onPostExecute( final Void unsed )
	{
		
	}

	protected Void doInBackground( Void... params )
	{
		try
		{
			DominioFotoSCN dominioFotoSCN = new DominioFotoSCN( AgendaActivity.context ) ;
			List<FotoVO> fotos = dominioFotoSCN.selectAllSended( ) ;
			for (int i = 0; i < fotos.size( ); i++)
			{
				FotoVO vo = ( FotoVO ) fotos.get( i ) ;
				if ( vo.getCaminho( ) != null )
				{
					File file = new File( vo.getCaminho( ) ) ;
					file.delete( ) ;					
				}
				dominioFotoSCN.deleteByVistoriaAndSequencia( vo.getId( ), vo.getSequencia( ) ) ;
			}
		}
		catch (Exception e)
		{
			e.printStackTrace( ) ;
			log.error( e.getMessage( ), e) ;
		}
		return null ;
	}
}
