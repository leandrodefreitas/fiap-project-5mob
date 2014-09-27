package br.com.fiap.minichef.services.tasks ;


import org.apache.log4j.Logger ;

import android.os.AsyncTask ;
import br.com.fiap.minichef.activity.AgendaActivity;
import br.com.fiap.minichef.services.scn.CadastroSCN;


public class ExecuteRegistrarFotoTask extends AsyncTask<Void, Void, Void>
{
	private final Logger log = Logger.getLogger( ExecuteRegistrarFotoTask.class ) ;
	
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
			CadastroSCN scn = new CadastroSCN( AgendaActivity.context ) ;
			scn.registrarFoto( AgendaActivity.matricula ) ;
		}
		catch (Exception e)
		{
			e.printStackTrace( ) ;
			log.error( e.getMessage( ), e) ;
		}
		return null ;
	}
}
