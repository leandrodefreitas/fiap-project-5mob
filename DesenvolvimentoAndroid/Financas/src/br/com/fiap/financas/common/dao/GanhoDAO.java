package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.GanhoVO;

public class GanhoDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_GANHOS
			+ " (descricao, valor, data, parcela, num_parcelas) values (?, ?, ?, ?, ?)";

	private static final String SELECT_ALL = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS;

	private static final String SELECT_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " order by data ASC";

	private static final String SELECT_BY_TIPO = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " where tipo = ";

	private SQLiteStatement insertStmt;

	public GanhoDAO(Context context) {
		super(context);
	}

	public long insert(GanhoVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindString(1, vo.getDescricao());
		this.insertStmt.bindDouble(2, vo.getValor());
		this.insertStmt.bindString(3, vo.getDataFormatted());
		this.insertStmt.bindLong(4, vo.getParcela());
		this.insertStmt.bindLong(5, vo.getNumParcelas());
		return this.insertStmt.executeInsert();
	}

	public long delete(GanhoVO vo) {
		return 0;
	}

	public void deleteAll() {
		super.database.delete(TABLE_GANHOS, null, null);
	}

	public List<GanhoVO> selectAll(int tipo) {

		List<GanhoVO> list = new ArrayList<GanhoVO>();

		String[] colunas = new String[] { "id", "descricao", "valor", "data",
				"parcela", "num_parcelas" };

		Cursor cursor = this.database.query(TABLE_GANHOS, colunas, null, null,
				null, null, "id");

		if (cursor.moveToFirst()) {
			do {
				GanhoVO ganho = new GanhoVO();
				ganho.setId(cursor.getInt(0));
				ganho.setDescricao(cursor.getString(1));
				ganho.setValor(cursor.getDouble(2));
				ganho.setData(cursor.getString(3));
				ganho.setParcela(cursor.getInt(4));
				ganho.setNumParcelas(cursor.getInt(5));

				list.add(ganho);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

}
