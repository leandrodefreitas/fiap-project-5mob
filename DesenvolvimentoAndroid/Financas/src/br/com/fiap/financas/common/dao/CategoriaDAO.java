package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.CategoriaVO;

public class CategoriaDAO extends DataSource {

	private static final String INSERT = "insert into " + TABLE_CATEGORIAS
			+ " (descricao) values (?)";

	private static final String SELECT_ALL = "select id, descricao from "
			+ TABLE_CATEGORIAS + " order by descricao";

	private SQLiteStatement insertStmt;

	public CategoriaDAO(Context context) {
		super(context);
	}

	public long insert(CategoriaVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindString(1, vo.getDescricao());
		return this.insertStmt.executeInsert();
	}

	public long delete(CategoriaVO vo) {
		return 0;
	}
	
	public void deleteAll() {
		super.database.delete(TABLE_CATEGORIAS, null, null);
	}	

	public List<CategoriaVO> selectAll() {

		List<CategoriaVO> list = new ArrayList<CategoriaVO>();

		Cursor cursor = database.rawQuery(SELECT_ALL, null);

		if (cursor.moveToFirst()) {
			do {
				CategoriaVO categoria = new CategoriaVO();
				categoria.setId(cursor.getInt(0));
				categoria.setDescricao(cursor.getString(1));

				list.add(categoria);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

}
