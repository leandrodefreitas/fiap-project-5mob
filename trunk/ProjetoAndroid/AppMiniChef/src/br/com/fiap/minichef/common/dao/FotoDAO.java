package br.com.fiap.minichef.common.dao ;

import java.util.ArrayList ;
  
import java.util.List ;

import android.content.Context ;
import android.database.Cursor ;
import android.database.sqlite.SQLiteStatement ;
import br.com.fiap.minichef.common.vo.FotoVO;

public class FotoDAO extends DataSource
{
	private static final String INSERT                     = "insert into " + TABLE_FOTO_NAME + " (matricula, id, status, caminho, sequencia) values ( ?, ?, ?, ?, ? )" ;
	private static final String UPDATE                     = "update " + TABLE_FOTO_NAME + " set status = ? where matricula = ? and id = ? " ;
	private static final String SELECT_ALL                 = "select id, status, caminho from " + TABLE_FOTO_NAME ;
	private static final String SELECT_BY_MATRICULA        = "select id, status, caminho, sequencia from " + TABLE_FOTO_NAME + " where matricula = ? and status = '" + FotoVO.NAO_ENVIADA + "'" ;
	private static final String SELECT_BY_VISTORIA         = "select matricula, status, caminho, sequencia from " + TABLE_FOTO_NAME + " where id = ?" ;
	private static final String SELECT_COUNT_BY_VISTORIA   = "select count() from fotos where id = ?" ;
	private static final String SELECT_MAX_SEQUENCE        = "select max( sequencia ) + 1 from " + TABLE_FOTO_NAME + " where matricula = ? and id = ?" ;
	private static final String DELETE				 	   = "delete from " + TABLE_FOTO_NAME + " where id = ? and sequencia = ?" ;
	private static final String SELECT_ALL_ENVIADA         = "select id, caminho from " + TABLE_FOTO_NAME + " where status = ?" ;

	private SQLiteStatement insertStmt ;
	private SQLiteStatement deleteStmt ;

	public FotoDAO( Context context )
	{
		super( context ) ;
		this.insertStmt = super.database.compileStatement( INSERT ) ;
	}

	public long insert( FotoVO vo )
	{
		this.insertStmt.bindString( 1, vo.getMatricula( ) ) ;
		this.insertStmt.bindString( 2, vo.getId( ) ) ;
		this.insertStmt.bindString( 3, vo.getStatus( ) ) ;
		this.insertStmt.bindString( 4, vo.getCaminho( ) ) ;
		this.insertStmt.bindLong( 5, vo.getSequencia( ) ) ;
		return this.insertStmt.executeInsert( ) ;
	}

	public long delete( FotoVO vo )
	{
		return 0 ;
	}

	public int getMaxSequence( String matricula, String id )
	{
		int retorno = 100 ;
		String[ ] args = { matricula, id } ;
		Cursor cursor = database.rawQuery( SELECT_MAX_SEQUENCE, args ) ;
		if (cursor.moveToFirst( ) )
		{
			retorno = cursor.getInt( 0 ) ;
			if ( retorno < 100 )
			{
				retorno = 100 ;
			}
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return retorno ;
	}

	public void deleteAll( )
	{
		super.database.delete( TABLE_FOTO_NAME, null, null ) ;
	}

	public void update( FotoVO vo )
	{
		String[ ] args =
		{ vo.getStatus( ), vo.getMatricula( ), vo.getId( ) } ;
		database.execSQL( UPDATE, args ) ;
	}

	public List<FotoVO> selectAll( )
	{
		List<FotoVO> list = new ArrayList<FotoVO>( ) ;
		String[ ] args =
		{ "" } ;
		Cursor cursor = database.rawQuery( SELECT_ALL, args ) ;
		if (cursor.moveToFirst( ))
		{
			do
			{
				FotoVO foto = new FotoVO( ) ;
				foto.setId( cursor.getString( 0 ) ) ;
				foto.setStatus( cursor.getString( 1 ) ) ;
				foto.setCaminho( cursor.getString( 2 ) ) ;
				foto.setSequencia( cursor.getInt( 3 ) ) ;
				list.add( foto ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}

	public List<FotoVO> selectByMatricula( String matricula )
	{
		List<FotoVO> list = new ArrayList<FotoVO>( ) ;

		String[ ] args = { matricula } ;
		Cursor cursor = database.rawQuery( SELECT_BY_MATRICULA, args ) ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				FotoVO foto = new FotoVO( ) ;
				foto.setId( cursor.getString( 0 ) ) ;
				foto.setStatus( cursor.getString( 1 ) ) ;
				foto.setCaminho( cursor.getString( 2 ) ) ;
				foto.setSequencia( cursor.getInt( 3 ) ) ;
				foto.setMatricula( matricula ) ;
				list.add( foto ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}
	
	public List<FotoVO> selectAllSended( String status )
	{
		List<FotoVO> list = new ArrayList<FotoVO>( ) ;

		String[ ] args = { status } ;
		Cursor cursor = database.rawQuery( SELECT_ALL_ENVIADA, args ) ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				FotoVO foto = new FotoVO( ) ;
				foto.setId( cursor.getString( 0 ) ) ;
				foto.setCaminho( cursor.getString( 1 ) ) ;
				list.add( foto ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}
	
	public List<FotoVO> selectByVistoria( String vistoria )
	{
		List<FotoVO> list = new ArrayList<FotoVO>( ) ;

		String[ ] args = { vistoria } ;
		Cursor cursor = database.rawQuery( SELECT_BY_VISTORIA, args ) ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				FotoVO foto = new FotoVO( ) ;
				foto.setId( vistoria ) ;
				foto.setMatricula( cursor.getString( 0 ) ) ;
				if (  cursor.getString( 1 ) == null )
				{
					foto.setStatus( FotoVO.NAO_ENVIADA ) ;
				}
				else
				{
					foto.setStatus( cursor.getString( 1 ) ) ;
				}
				foto.setCaminho( cursor.getString( 2 ) ) ;
				foto.setSequencia( cursor.getInt( 3 ) ) ;
				list.add( foto ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return list ;
	}

	public int getCountByVistoria( int vistoria )
	{
		String[ ] args =
		{ Integer.toString( vistoria ) } ;
		Cursor cursor = database.rawQuery( SELECT_COUNT_BY_VISTORIA, args ) ;

		int totalFotosVistoria = 0 ;

		if (cursor.moveToFirst( ))
		{
			do
			{
				totalFotosVistoria = cursor.getInt( 0 ) ;
			}
			while (cursor.moveToNext( )) ;
		}
		if (cursor != null && !cursor.isClosed( ))
		{
			cursor.close( ) ;
		}
		return totalFotosVistoria ;
	}
	
	public int deleteByVistoriaAndSequencia( String vistoria, int sequencia )
	{
		this.deleteStmt = super.database.compileStatement( DELETE );
		this.deleteStmt.bindString( 1, vistoria );
		this.deleteStmt.bindLong( 2, sequencia );

		return this.deleteStmt.executeUpdateDelete( ) ;
	}
}