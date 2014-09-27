package br.com.fiap.minichef.common.dao ;

import java.util.ArrayList ;
import java.util.List ;

import android.content.Context ;
import android.database.Cursor ;
import android.database.sqlite.SQLiteStatement ;
import br.com.fiap.minichef.common.vo.JogoVO;

public class JogoDAO extends DataSource
{
	private static final String INSERT = "insert into " + TABLE_JOGO_NAME
			+ " (nome, numeros) values (?,?)" ;

	private SQLiteStatement insertStmt ;

	public JogoDAO( Context context )
	{
		super( context ) ;
		this.insertStmt = super.database.compileStatement( INSERT ) ;
	}

	public long insert( JogoVO jogo )
	{
		this.insertStmt.bindString( 1, jogo.getNome( ) ) ;
		this.insertStmt.bindString( 2, jogo.getNumeros( ) ) ;
		return this.insertStmt.executeInsert( ) ;
	}

	public JogoVO selecionarPorNome( String nome )
	{
		String[ ] campos = new String[ ]
		{ "id", "nome", "numeros" } ;
		String[ ] args = new String[ ]
		{ nome } ;
		Cursor c = database.query( TABLE_JOGO_NAME, campos, "nome=?", args, null, null, null ) ;
		if (c.moveToFirst( ))
		{
			JogoVO jogo = new JogoVO( ) ;
			jogo.setId( c.getInt( 0 ) ) ;
			jogo.setNome( c.getString( 1 ) ) ;
			jogo.setNumeros( c.getString( 2 ) ) ;
			return jogo ;
		}
		else
		{
			return null ;
		}
	}

	public JogoVO selecionarPorNumeros( String numeros )
	{
		String[ ] campos = new String[ ]
		{ "id", "nome", "numeros" } ;
		String[ ] args = new String[ ]
		{ numeros } ;
		Cursor c = database.query( TABLE_JOGO_NAME, campos, "numeros=?", args, null, null, null ) ;
		if (c.moveToFirst( ))
		{
			JogoVO jogo = new JogoVO( ) ;
			jogo.setId( c.getInt( 0 ) ) ;
			jogo.setNome( c.getString( 1 ) ) ;
			jogo.setNumeros( c.getString( 2 ) ) ;
			return jogo ;
		}
		else
		{
			return null ;
		}
	}

	public void excluirPorId( int id )
	{
		String[ ] args = new String[ ]
		{ String.valueOf( id ) } ;
		this.database.delete( TABLE_JOGO_NAME, "id=?", args ) ;
	}

	public void deleteAll( )
	{
		this.database.delete( TABLE_JOGO_NAME, null, null ) ;
	}

	public List<JogoVO> selectAll( )
	{
		List<JogoVO> list = new ArrayList<JogoVO>( ) ;
		Cursor cursor = this.database.query( TABLE_JOGO_NAME, new String[ ]
		{ "id", "nome", "numeros" }, null, null, null, null, "id" ) ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				JogoVO jogo = new JogoVO( ) ;
				jogo.setId( cursor.getInt( 0 ) ) ;
				jogo.setNome( cursor.getString( 1 ) ) ;
				jogo.setNumeros( cursor.getString( 2 ) ) ;
				list.add( jogo ) ;
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