package br.com.fiap.minichef.install;

import java.io.FileOutputStream ;
import java.io.IOException ;
import java.io.InputStream ;
import java.net.SocketException ;

import org.apache.commons.net.ftp.FTPClient ;
import org.apache.log4j.Logger ;

import android.content.Context ;
import android.content.pm.PackageInfo ;
import android.os.StrictMode ;
import br.com.fiap.minichef.activity.AgendaActivity;
import br.com.fiap.minichef.util.Environment;
import br.com.fiap.minichef.util.log.HandlerLog;

public class VerifyInstall extends Updater
{
	private final Logger log = Logger.getLogger( VerifyInstall.class ) ;

	public FTPClient mFTPClient = null ;

	public VerifyInstall( Context context )
	{
		super( context ) ;
		
		if (android.os.Build.VERSION.SDK_INT > 9)
		{
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder( ).permitAll( )
					.build( ) ;
			StrictMode.setThreadPolicy( policy ) ;
		}
	}

	@Override
	public boolean hasUpdate( )
	{
		HandlerLog.configure( ) ;
		log.info( "iniciando a verificacao de versionamento da APK ::: versao.txt" ) ;
		try
		{
			FTPClient ftp = new FTPClient( ) ;
			ftp.connect( Environment.FTP_HOST, Environment.FTP_PORT ) ;
			ftp.login( Environment.FTP_LOGIN, Environment.FTP_PASSWORD ) ;
			ftp.setBufferSize( 1024 * 1024 ) ;
			ftp.changeWorkingDirectory( Environment.FTP_APK_WORKING_DIR ) ;
			ftp.setFileType( FTPClient.BINARY_FILE_TYPE ) ;
			ftp.enterLocalPassiveMode( ) ;
			
			String[ ] listFiles = new String[] { "versao.txt" };

			StringBuffer  versao = new StringBuffer( ) ;
			log.info( "carregando arquivo de versionamento: " + listFiles[ 0 ] ) ;
			try
			{
				InputStream   is     = ftp.retrieveFileStream( listFiles[ 0 ] ) ;
				int           i      = 0 ;
				while( ( i = is.read( ) ) != -1 )
				{
					versao.append( (char ) i ) ;
				}
				is.close( ) ;
				
				log.info( "download arquivo de versao: " + listFiles[ 0 ] 	+ " efetuado com sucesso! " ) ;
			}
			catch (Exception e)
			{
				log.info( "erro ao efetuar download do arquivo: " + listFiles[ 0 ] ) ;
				log.error( e.getMessage( ), e) ;
			}
			
			log.info( "processo download do arquivo de versionamento finalizado" ) ; 
			ftp.disconnect( ) ;

			PackageInfo infoPrograma = context.getPackageManager( ).getPackageInfo( context.getPackageName( ), 0 ) ;
			
			Double versaoLong   = Double.parseDouble( versao.toString( ) );  
			Double programaLong = Double.parseDouble( infoPrograma.versionName ) ;
			System.out.println( "Versao: " + versaoLong.toString( ) ) ;
			System.out.println( "Programa: " + programaLong.toString( ) ) ;
			
			if ( versaoLong.doubleValue( ) >  programaLong.doubleValue( ) )
			{
				AgendaActivity.versao = versaoLong.toString( ) ;
			}
			else
			{
				AgendaActivity.versao = programaLong.toString( ) ;
			}

			return ( versaoLong.doubleValue( ) >  programaLong.doubleValue( ) ) ;
		}
		catch (SocketException e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
			return false ;
		}
		catch (IOException e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
			return false ;
		}
		catch (Exception e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
			return false ;
		}
	}

	@Override
	public void update( )
	{
		HandlerLog.configure( ) ;
		log.info( "iniciando a atualização da APK ::: FIAP.apk" ) ;
		try
		{
			FTPClient ftp = new FTPClient( ) ;
			ftp.connect( Environment.FTP_HOST, Environment.FTP_PORT ) ;
			ftp.login( Environment.FTP_LOGIN, Environment.FTP_PASSWORD ) ;
			ftp.setBufferSize( 1024 * 1024 ) ;
			ftp.changeWorkingDirectory( Environment.FTP_APK_WORKING_DIR ) ;
			ftp.setFileType( FTPClient.BINARY_FILE_TYPE ) ;
			ftp.enterLocalPassiveMode( ) ;
			
			String[ ] listFiles = new String[] { "FIAP.apk" };

			for (int i = 0; i < listFiles.length; i++)
			{
				log.info( "carregando arquivo de apk: " + listFiles[ 0 ] ) ;
				FileOutputStream fos ;
				try
				{
					fos = context.openFileOutput( listFiles[ i ], Context.MODE_APPEND ) ;
					ftp.retrieveFile( listFiles[ i ], fos ) ;
					fos.flush( ) ;
					fos.close( ) ;
					log.info( "download do arquivo apk: " + listFiles[ 0 ] 	+ " efetuado com sucesso! " ) ;
				}
				catch (Exception e)
				{
					log.info( "erro ao efetuar download do arquivo: " + listFiles[ 0 ] ) ;
				}
			}
			log.info( "processo download finalizado" ) ;
			ftp.disconnect( ) ;
			try 
			{
				String command = "chmod 666 " + PATH;
				Runtime.getRuntime().exec(command);
			} 
			catch ( IOException e ) 
			{
				log.error( e.getMessage( ), e) ;
			}
		}
		catch (SocketException e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
		}
		catch (IOException e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
		}
		catch (Exception e)
		{
			e.printStackTrace( ) ;
			log.error( e ) ;
		}
	}
}
