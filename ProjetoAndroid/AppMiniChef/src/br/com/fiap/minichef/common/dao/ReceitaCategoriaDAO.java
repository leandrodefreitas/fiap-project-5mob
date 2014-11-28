package br.com.fiap.minichef.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.minichef.common.vo.ReceitaCategoriaVO;

public class ReceitaCategoriaDAO extends DataSource{

	private static final String INSERT = "insert into " + TABLE_RECEITA_CATEGORIA + " (id_receita, id_categoria, tipo) values (?, ?, ?) ";
	
	private static final String SELECT_ALL = "select id, id_receita, id_categoria, tipo from " + TABLE_RECEITA_CATEGORIA + " order by id";

	private static final String SELECT_BY_ID_REGISTRO_TIPO = "select id, id_receita, id_categoria, tipo from " + TABLE_RECEITA_CATEGORIA + " where id_receita = ? and tipo = ?";
	
	private static final String SELECT_BY_ID_CATEGORIA_TIPO = "select id, id_receita, id_categoria, tipo from " + TABLE_RECEITA_CATEGORIA + " where id_categoria = ? and tipo = ?";
	
	private SQLiteStatement insertStmt;
	
	public ReceitaCategoriaDAO(Context context) {
		super(context);
	}
	
	public long insert(ReceitaCategoriaVO vo){
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindLong(1, vo.getIdReceita());
		this.insertStmt.bindLong(2, vo.getIdCategoria());
		this.insertStmt.bindLong(3, vo.getTipo());
		return this.insertStmt.executeInsert();
	}
	
	public long delete(ReceitaCategoriaVO vo) {
		return 0;
	}
	
	public void deleteAll() {
		super.database.delete(TABLE_RECEITA_CATEGORIA, null, null);
	}		
	
	public List<ReceitaCategoriaVO> selectAll() {

		List<ReceitaCategoriaVO> list = new ArrayList<ReceitaCategoriaVO>();

		String[] args = {""};
		Cursor cursor = database.rawQuery(SELECT_ALL, args);

		if (cursor.moveToFirst()) {
			do {
				ReceitaCategoriaVO receitaCategoria = new ReceitaCategoriaVO();
				receitaCategoria.setId(cursor.getInt(0));
				receitaCategoria.setIdReceita(cursor.getInt(1));
				receitaCategoria.setIdCategoria(cursor.getInt(2));
				receitaCategoria.setTipo(cursor.getInt(3));
				list.add(receitaCategoria);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<ReceitaCategoriaVO> selectByIdTipo(Integer id, Integer tipo) {

		List<ReceitaCategoriaVO> list = new ArrayList<ReceitaCategoriaVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_REGISTRO_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				ReceitaCategoriaVO receitaCategoria = new ReceitaCategoriaVO();
				receitaCategoria.setId(cursor.getInt(0));
				receitaCategoria.setIdReceita(cursor.getInt(1));
				receitaCategoria.setIdCategoria(cursor.getInt(2));
				receitaCategoria.setTipo(cursor.getInt(3));
				list.add(receitaCategoria);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<ReceitaCategoriaVO> selectByIdCategoria(Integer id, Integer tipo) {

		List<ReceitaCategoriaVO> list = new ArrayList<ReceitaCategoriaVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_CATEGORIA_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				ReceitaCategoriaVO receitaCategoria = new ReceitaCategoriaVO();
				receitaCategoria.setId(cursor.getInt(0));
				receitaCategoria.setIdReceita(cursor.getInt(1));
				receitaCategoria.setIdCategoria(cursor.getInt(2));
				receitaCategoria.setTipo(cursor.getInt(3));
				list.add(receitaCategoria);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}	
		
	
}
