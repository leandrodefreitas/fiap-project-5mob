package br.com.fiap.minichef.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.minichef.common.vo.IngredienteVO;

public class IngredienteDAO extends DataSource {

	private static final String INSERT = "insert into " + TABLE_INGREDIENTES
			+ " (descricao) values (?)";

	private static final String SELECT_ALL = "select id, descricao from "
			+ TABLE_INGREDIENTES + " order by descricao";
	
	private static final String SELECT_BY_ID = "select id, descricao from "
			+ TABLE_INGREDIENTES + " where id = ? ";

	private SQLiteStatement insertStmt;

	public IngredienteDAO(Context context) {
		super(context);
	}

	public long insert(IngredienteVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindString(1, vo.getDescricao());
		return this.insertStmt.executeInsert();
	}

	public long delete(IngredienteVO vo) {
		return 0;
	}
	
	public void deleteAll() {
		super.database.delete(TABLE_INGREDIENTES, null, null);
	}	

	public List<IngredienteVO> selectAll() {

		List<IngredienteVO> list = new ArrayList<IngredienteVO>();

		Cursor cursor = database.rawQuery(SELECT_ALL, null);

		if (cursor.moveToFirst()) {
			do {
				IngredienteVO ingrediente = new IngredienteVO();
				ingrediente.setId(cursor.getInt(0));
				ingrediente.setDescricao(cursor.getString(1));

				list.add(ingrediente);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public IngredienteVO selectById(Integer id) {
		
		String[] args = {String.valueOf(id)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID, args);

		if (cursor.moveToFirst()) {
			
			IngredienteVO ingrediente = new IngredienteVO();
			ingrediente.setId(cursor.getInt(0));
			ingrediente.setDescricao(cursor.getString(1));
			return ingrediente;
			
		} else {
			
			return null;
		}
	}	

}
