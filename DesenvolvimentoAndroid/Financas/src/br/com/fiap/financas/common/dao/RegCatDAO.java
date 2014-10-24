package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.RegCatVO;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

public class RegCatDAO extends DataSource{

	private static final String INSERT = "insert into" + TABLE_REGISTRO_CATEGORIA + " (codigo, cod_reg, cod_Cat) values (?, ?) ";
	
	private static final String SELECT_ALL = "select codigo, cod_reg, cod_cat from " + TABLE_REGISTRO_CATEGORIA + "order by codigo";
	
	
	private SQLiteStatement insertStmt;
	
	public RegCatDAO(Context context) {
		super(context);
	}
	
	public long insert(RegCatVO vo){
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindLong(1, vo.getCodigo());
		this.insertStmt.bindLong(2, vo.getCod_reg());
		this.insertStmt.bindLong(3, vo.getCod_cat());
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
				regcat.setCodigo(cursor.getInt(0));
				regcat.setCod_reg(cursor.getInt(1));
				regcat.setCod_reg(cursor.getInt(2));
				list.add(regcat);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
}
