package br.com.fiap.minichef.util ;

import android.content.Context ;
import android.net.ConnectivityManager ;

public class InternetConnection
{

	public static boolean verificandoConexao( Context contexto )
	{
		boolean conectado ;
		ConnectivityManager conectivtyManager = ( ConnectivityManager ) contexto
				.getSystemService( Context.CONNECTIVITY_SERVICE ) ;
		if (conectivtyManager.getActiveNetworkInfo( ) != null
				&& conectivtyManager.getActiveNetworkInfo( ).isAvailable( )
				&& conectivtyManager.getActiveNetworkInfo( ).isConnected( ))
		{
			conectado = true ;
		}
		else
		{
			conectado = false ;
		}
		return conectado ;
	}
}
