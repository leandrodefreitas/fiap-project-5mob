package br.com.fiap.minichef.common.dao ;

import java.io.File;

import android.content.Context ;
import android.database.sqlite.SQLiteDatabase ;
import android.database.sqlite.SQLiteOpenHelper ;
import android.os.Environment;

public class DataSource extends SQLiteOpenHelper
{
	protected Context context ;
	protected SQLiteDatabase database ;

	public static String DATABASE_COMPLETE                       = "" ;
	public static final String DATABASE_NAME                     = "fiapdb.db" ;
	public static final String DATABASE_NAME_JOURNAL             = "fiapdb.db-journal" ;
	public static final int DATABASE_VERSION                     = 10 ;
	
	public static int SYNONYM_DEFAULT                            = 0 ; 
	public static int SYNONYM                                    ;
	public static int SYNONYM_FTP                                ;
	
	protected static final String TABLE_CONTATO_NAME             = "CONTATOS" + "_" + SYNONYM  ;
	protected static final String TABLE_FOTO_NAME                = "FOTOS" + "_" + SYNONYM  ;
	
	protected static final String TABLE_JOGO_NAME                = "JOGOS" ;
	
	static
	{
		if ( br.com.fiap.minichef.util.Environment.DEVELOPMENT )
		{
			DATABASE_COMPLETE = DATABASE_NAME ;
		}
		else
		{
			DATABASE_COMPLETE = Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + DATABASE_NAME ;
		}
	}
	
	public boolean createAllTables( )
	{
		return false ;
	}

	public boolean cleanUp( )
	{
		return false ;
	}

	public DataSource( Context context )
	{
		super( context, DATABASE_COMPLETE, null, DATABASE_VERSION ) ;
		this.context  = context ;
		this.database = getWritableDatabase( ) ;
	}
	
	public void onCreate( SQLiteDatabase db )
	{
		db.execSQL( "CREATE TABLE CONTATOS_0 (codigo INTEGER, nome TEXT, endereco TEXT, telefone TEXT)" ) ;
		db.execSQL( "CREATE TABLE CONTATOS_1 (codigo INTEGER, nome TEXT, endereco TEXT, telefone TEXT)" ) ;
		db.execSQL( "CREATE TABLE FOTOS_0 (codigo TEXT, id TEXT, status TEXT, caminho TEXT, sequencia INTEGER)" ) ;
		db.execSQL( "CREATE TABLE FOTOS_1 (codigo TEXT, id TEXT, status TEXT, caminho TEXT, sequencia INTEGER)" ) ;
		db.execSQL( "CREATE TABLE JOGOS (id INTEGER PRIMARY KEY AUTOINCREMENT, nome TEXT, numeros TEXT)" ) ;
	}

	public void onUpgrade( SQLiteDatabase db, int oldVersion, int newVersion )
	{
		db.execSQL( "DROP TABLE IF EXISTS CONTATOS_0"   ) ;
		db.execSQL( "DROP TABLE IF EXISTS CONTATOS_1"   ) ;
		db.execSQL( "DROP TABLE IF EXISTS FOTOS_0"      ) ;
		db.execSQL( "DROP TABLE IF EXISTS FOTOS_1"      ) ;
		db.execSQL( "DROP TABLE IF EXISTS JOGOS"      ) ;
		onCreate( db ) ;
	}

	public void close( )
	{
		this.database.close( ) ;
	}
}
