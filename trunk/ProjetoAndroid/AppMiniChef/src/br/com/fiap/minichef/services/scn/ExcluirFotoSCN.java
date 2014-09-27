package br.com.fiap.minichef.services.scn ;


import java.util.Timer ;
import java.util.TimerTask ;

import org.apache.log4j.Logger ;

import android.os.Handler ;
import br.com.fiap.minichef.services.tasks.ExecuteExcluirFotoTask;
import br.com.fiap.minichef.util.Environment;

public class ExcluirFotoSCN 
{
	private static final Logger log = Logger.getLogger( ExcluirFotoSCN.class ) ;
	
	public static void callAsynchronousTask( )
	{
		final Handler handler = new Handler( ) ;
		Timer timer = new Timer( ) ;
		TimerTask doAsynchronousTask = new TimerTask( )
		{
			@Override
			public void run( )
			{
				handler.post( new Runnable( )
				{
					public void run( )
					{
						try
						{
							ExecuteExcluirFotoTask excluirFotoTask = new ExecuteExcluirFotoTask( ) ;
							excluirFotoTask.execute( ) ;
						}
						catch (Exception e)
						{
							e.printStackTrace( ) ;
							log.error( e.getMessage( ), e) ;
						}
					}
				} ) ;
			}
		} ;
		timer.schedule( doAsynchronousTask, 0, Environment.TIMER_DELETE_PHOTO ) ;
	}
}
