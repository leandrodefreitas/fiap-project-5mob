package br.com.fiap.minichef.util.log ;

import java.io.File ;

import org.apache.log4j.Level ;

import android.os.Environment ;
import de.mindpipe.android.logging.log4j.LogConfigurator ;

public class HandlerLog
{
	public static String LOG_FILE_NAME = "fiap.log" ;
	
	public static void configure( )
	{
		final LogConfigurator logConfigurator = new LogConfigurator( ) ;

		logConfigurator.setFileName( Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + LOG_FILE_NAME ) ;
		logConfigurator.setRootLevel( Level.INFO ) ;
		logConfigurator.setLevel( "org.apache", Level.ERROR ) ;
		logConfigurator.setMaxFileSize( 10 * 1024 * 1024 ) ;
		logConfigurator.configure( ) ;
	}
}
