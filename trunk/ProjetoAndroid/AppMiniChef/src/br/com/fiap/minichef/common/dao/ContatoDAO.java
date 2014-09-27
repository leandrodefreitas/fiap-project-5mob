package br.com.fiap.minichef.common.dao ;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.minichef.common.vo.ContatoVO;


public class ContatoDAO extends DataSource
{
	private static final String INSERT_FTP			= "insert into " + TABLE_CONTATO_NAME + " (codigo, nome, endereco, telefone ) values ( ?, ?, ?, ? )" ;
	private static final String INSERT 				= "insert into " + TABLE_CONTATO_NAME + " (codigo, nome, endereco, telefone ) values ( ?, ?, ?, ? )" ;
	private static final String SELECT_BY_NAME  	= "select codigo, nome, endereco, telefone from " + TABLE_CONTATO_NAME	+ " order by nome ASC" ;

	private SQLiteStatement insertStmt ;

	public ContatoDAO( Context context )
	{
		super( context ) ;
		this.insertStmt = super.database.compileStatement( INSERT ) ;
	}
	
	public ContatoDAO( Context context, boolean loading )
	{
		super( context ) ;
		if ( loading )
		{
			this.insertStmt = super.database.compileStatement( INSERT_FTP ) ;
		}
	}

	public long insert( ContatoVO vo )
	{
		this.insertStmt.bindLong(   1, vo.getCodigo( ) ) ;
		this.insertStmt.bindString( 2, vo.getNome( ) ) ;
		this.insertStmt.bindString( 3, vo.getEndereco( ) ) ;
		this.insertStmt.bindString( 4, vo.getTelefone( ) ) ;
		return this.insertStmt.executeInsert( ) ;
	}

	public long delete( ContatoVO vo )
	{
		return 0 ;
	}

	public void deleteAll( )
	{
		super.database.delete( TABLE_CONTATO_NAME, null, null ) ;
	}

	public List<ContatoVO> selectAll( )
	{
		List<ContatoVO> list = new ArrayList<ContatoVO>( ) ;
		Cursor cursor = this.database.query( TABLE_CONTATO_NAME, new String[ ]
		{ "codigo", "nome", "endereco", "telefone" }, null, null, null, null, "codigo" ) ;
		if (cursor.moveToFirst( ))
		{
			do
			{
				ContatoVO contato = new ContatoVO( ) ;
				contato.setCodigo(   cursor.getInt(    0 ) ) ;
				contato.setNome(     cursor.getString( 1 ) ) ;
				contato.setEndereco( cursor.getString( 2 ) ) ;
				contato.setTelefone( cursor.getString( 3 ) ) ;
				list.add( contato ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}
	
	public List<ContatoVO> selectAllOrderName( )
	{
		List<ContatoVO>    list   = new ArrayList<ContatoVO>( ) ;
		Cursor             cursor = database.rawQuery( SELECT_BY_NAME, null ) ;
		ContatoVO contato         ;
		if (cursor.moveToFirst( ))
		{
			do
			{
				contato = new ContatoVO( ) ;
				contato.setCodigo(   cursor.getInt(    0 ) ) ;
				contato.setNome(     cursor.getString( 1 ) ) ;
				contato.setEndereco( cursor.getString( 2 ) ) ;
				contato.setTelefone( cursor.getString( 3 ) ) ;
				
				list.add( contato ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}
}