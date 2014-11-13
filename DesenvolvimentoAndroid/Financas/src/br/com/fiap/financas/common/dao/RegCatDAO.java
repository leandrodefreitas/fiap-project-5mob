package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.RegCatVO;

public class RegCatDAO extends DataSource{

	private static final String INSERT = "insert into " + TABLE_REGISTRO_CATEGORIA + " (id_registro, id_categoria, tipo) values (?, ?, ?) ";
	
	private static final String SELECT_ALL = "select id, id_registro, id_categoria, tipo from " + TABLE_REGISTRO_CATEGORIA + " order by id";

	private static final String SELECT_BY_ID_REGISTRO_TIPO = "select id, id_registro, id_categoria, tipo from " + TABLE_REGISTRO_CATEGORIA + " where id_registro = ? and tipo = ?";
	
	private static final String SELECT_BY_ID_CATEGORIA_TIPO = "select id, id_registro, id_categoria, tipo from " + TABLE_REGISTRO_CATEGORIA + " where id_categoria = ? and tipo = ?";
	
	private SQLiteStatement insertStmt;
	
	public RegCatDAO(Context context) {
		super(context);
	}
	
	public long insert(RegCatVO vo){
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindLong(1, vo.getIdRegistro());
		this.insertStmt.bindLong(2, vo.getIdCategoria());
		this.insertStmt.bindLong(3, vo.getTipo());
		return this.insertStmt.executeInsert();
	}
	
	public long delete(RegCatVO vo) {
		return 0;
	}
	
	public void deleteAll() {
		super.database.delete(TABLE_REGISTRO_CATEGORIA, null, null);
	}		
	
	public List<RegCatVO> selectAll() {

		List<RegCatVO> list = new ArrayList<RegCatVO>();

		String[] args = {""};
		Cursor cursor = database.rawQuery(SELECT_ALL, args);

		if (cursor.moveToFirst()) {
			do {
				RegCatVO regcat = new RegCatVO();
				regcat.setId(cursor.getInt(0));
				regcat.setIdRegistro(cursor.getInt(1));
				regcat.setIdCategoria(cursor.getInt(2));
				regcat.setTipo(cursor.getInt(3));
				list.add(regcat);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<RegCatVO> selectByIdTipo(Integer id, Integer tipo) {

		List<RegCatVO> list = new ArrayList<RegCatVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_REGISTRO_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				RegCatVO regcat = new RegCatVO();
				regcat.setId(cursor.getInt(0));
				regcat.setIdRegistro(cursor.getInt(1));
				regcat.setIdCategoria(cursor.getInt(2));
				regcat.setTipo(cursor.getInt(3));
				list.add(regcat);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<RegCatVO> selectByIdCategoria(Integer id, Integer tipo) {

		List<RegCatVO> list = new ArrayList<RegCatVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_CATEGORIA_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				RegCatVO regcat = new RegCatVO();
				regcat.setId(cursor.getInt(0));
				regcat.setIdRegistro(cursor.getInt(1));
				regcat.setIdCategoria(cursor.getInt(2));
				regcat.setTipo(cursor.getInt(3));
				list.add(regcat);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}	
		
	
}
