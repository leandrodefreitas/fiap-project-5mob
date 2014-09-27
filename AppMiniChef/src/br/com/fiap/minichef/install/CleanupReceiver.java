package br.com.fiap.minichef.install;

import java.io.File ;

import android.content.BroadcastReceiver ;
import android.content.Context ;
import android.content.Intent ;
import android.net.Uri ;
import android.os.Environment ;
import android.util.Log ;
import br.com.fiap.minichef.common.dao.DataSource;
import br.com.fiap.minichef.util.log.HandlerLog;

public class CleanupReceiver extends BroadcastReceiver
{
	@Override
	public void onReceive( Context context, Intent intent )
	{
		Uri data = intent.getData();
		Log.d("FIAP", "Action: " + intent.getAction());
		Log.d("FIAP", "The DATA: " + data);
		
		if( intent.getAction().equals( Intent.ACTION_PACKAGE_REMOVED ) )
		{
			String databaseName         = null ;
			String databaseNameJournal  = null ;
			String logfileName          = null ;
			String apkfileName          = null ;
			
			if ( br.com.fiap.minichef.util.Environment.DEVELOPMENT )
			{
				databaseName        = File.separator + DataSource.DATABASE_NAME ;
				databaseNameJournal = File.separator + DataSource.DATABASE_NAME_JOURNAL ;
				logfileName         = File.separator + HandlerLog.LOG_FILE_NAME ;
				apkfileName         = File.separator + Updater.SERVICE_RELEASE ;
			}
			else
			{
				databaseName        = Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + DataSource.DATABASE_NAME ;
				databaseNameJournal = Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + DataSource.DATABASE_NAME_JOURNAL ;
				logfileName         = Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + HandlerLog.LOG_FILE_NAME ;
				apkfileName         = context.getFilesDir().getAbsolutePath() + File.separator + Updater.SERVICE_RELEASE ;
			}
				
			System.out.println( " databaseName : " + databaseName ) ;
			System.out.println( " databaseNameJournal : " + databaseNameJournal ) ;
			System.out.println( " logfileName : " + logfileName ) ;
			System.out.println( " apkfileName : " + apkfileName ) ;
			
			File file = new File( databaseName );
		    if ( file != null ) 
		    {
		        file.delete( ) ;
		    }

			file = new File( databaseNameJournal );
		    if ( file != null ) 
		    {
		        file.delete( ) ;
		    }

		    file = new File( logfileName );
		    if ( file != null ) 
		    {
		        file.delete( ) ;
		    }
		    
		    file = new File( apkfileName );
		    if ( file != null ) 
		    {
		        file.delete( ) ;
		    }
		}
	}

}
