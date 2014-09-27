package br.com.fiap.minichef.util ;

import java.io.InputStream ;
import java.security.KeyStore ;

import org.apache.http.conn.ClientConnectionManager ;
import org.apache.http.conn.scheme.PlainSocketFactory ;
import org.apache.http.conn.scheme.Scheme ;
import org.apache.http.conn.scheme.SchemeRegistry ;
import org.apache.http.conn.ssl.SSLSocketFactory ;
import org.apache.http.impl.client.DefaultHttpClient ;
import org.apache.http.impl.conn.SingleClientConnManager ;

import android.content.Context ;

public class FIAPHttpClient extends DefaultHttpClient
{
	final Context context ;

	public FIAPHttpClient( Context context )
	{
		this.context = context ;
	}

	@Override
	protected ClientConnectionManager createClientConnectionManager( )
	{
		SchemeRegistry registry = new SchemeRegistry( ) ;
		registry.register( new Scheme( "http", PlainSocketFactory.getSocketFactory( ), 80 ) ) ;
		registry.register( new Scheme( "https", newSslSocketFactory( ), 443 ) ) ;
		return new SingleClientConnManager( getParams( ), registry ) ;
	}

	private SSLSocketFactory newSslSocketFactory( )
	{
		try
		{
			KeyStore trusted = KeyStore.getInstance( "BKS" ) ;
			InputStream in   = null ;
			
			if ( Environment.SETTING_PROD )
			{
				in = context.getResources( ).getAssets( ).open( "client_prod.bks" ) ;
			}
			else
			{
				in = context.getResources( ).getAssets( ).open( "client.bks" ) ;
			}
			try
			{
				String value = "changeit" ;
				if ( value != null )
				{
					trusted.load( in, value.toCharArray( ) ) ;
				}
			}
			finally
			{
				in.close( ) ;
			}
			SSLSocketFactory sf = new SSLSocketFactory( trusted ) ;
			sf.setHostnameVerifier( SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER ) ;
			return sf ;
		}
		catch (Exception e)
		{
			throw new AssertionError( e ) ;
		}
	}
}