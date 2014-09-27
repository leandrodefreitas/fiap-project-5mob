package br.com.fiap.minichef.util;

public class Environment
{
	public static final long TIMER_MESSAGE                     = 10000 ;
	
	public static final long WAIT_TIMER_MESSAGE                = 3000 ;
	
	public static final boolean DEVELOPMENT                    = true ;
	
	// 20 em 20 minutos
	public static final long TIMER_UPDATE_GPS			       = 2 * 60 * 1000 ;
	
	// 1 em 1 hora
	public static final long TIMER_PHOTO				       = 20 * 60 * 1000 ;
	
	// 2 em 2 hora 
	public static final long TIMER_DELETE_PHOTO			       = 30 * 60 * 1000 ;
	
	// 10 em 10 minutos
	public static final long TIMER_PREVER_AGD			       = 1 * 60 * 1000 ;
	
	// 2 em 2 minutos
	public static final long TIMER_WAIT_GPS			           = 30 * 1000 ;
	public static final int  MAX_TRIES_GPS				       = 8 ;
	
	public static boolean SETTING_PROD                         = false ;
	public static boolean LAYOUT_CLARO                         = false ;

	// FTP - Ambiente de Homologacao e Producao
	public static final String FTP_HOST                        = "ftp.fiap.com.br" ;
	public static final String FTP_LOGIN                       = "usuario" ;
	public static final String FTP_PASSWORD                    = "senha" ;
	public static final int    FTP_PORT                        = 21 ;
	public static final String FTP_WORKING_DIR                 = "/temp/arquivos" ;
	public static final String FTP_APK_WORKING_DIR             = "/temp/apk" ;
	
	// Dados de Seguranca - PRODUCAO
	public static final String PROD_WS_HOST                    = "wwws.fiap.com.br" ;
	public static final String PROD_WS_LOGIN                   = "usuario" ;
	public static final String PROD_WS_PASSWORD                = "senha" ;

	
	public static void configureEnvironment( boolean prod )
	{
		if ( prod )
		{
			SETTING_PROD                    = true ;
		}
		else
		{
			SETTING_PROD                    = false ;
		}
	}
	
}
