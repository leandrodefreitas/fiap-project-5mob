package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.GastoVO;

public class GastoDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_GASTOS
			+ " (descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto) values (?, ?, ?, ?, ?, ?, ?, ? )";
	
	
	private static final String SELECT_ALL = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS;
	
	private static final String SELECT_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS + " order by data ASC";

	private static final String SELECT_BY_TIPO = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS + " where tipo = ";

	private SQLiteStatement insertStmt;

	public GastoDAO(Context context) {
		super(context);
	}

	public long insert(GastoVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindString(1, vo.getDescricao());
		this.insertStmt.bindDouble(2, vo.getValor());
		this.insertStmt.bindString(3, vo.getDataFormatted());
		this.insertStmt.bindLong(4, vo.getParcela());
		this.insertStmt.bindLong(5, vo.getNumParcelas());
		this.insertStmt.bindLong(6, vo.getIdGanho());
		this.insertStmt.bindString(7, vo.getLocal());
		this.insertStmt.bindString(8, vo.getFoto());
		return this.insertStmt.executeInsert();
	}

	public long delete(GastoVO vo) {
		return 0;
	}

	public void deleteAll() {
		super.database.delete(TABLE_GASTOS, null, null);
	}
	
	public List<GastoVO> selectByType(int tipo) {
		
		List<GastoVO> list = new ArrayList<GastoVO>();
		
		String[] colunas = new String[]{"codigo", "tipo", "descricao", "valor", "data", "parcela", "num_parcelas", "local", "foto"};
		String[ ] args = new String[ ]
		{ String.valueOf(tipo) } ;
		
		Cursor cursor = this.database.query(TABLE_GASTOS, colunas, "tipo=?", args, null, null, "codigo");
		
		if (cursor.moveToFirst()) {
			do {
				GastoVO gasto = new GastoVO();
				gasto.setId(cursor.getInt(0));
				gasto.setDescricao(cursor.getString(1));
				gasto.setValor(cursor.getDouble(2));
				gasto.setData(cursor.getString(3));
				gasto.setParcela(cursor.getInt(4));
				gasto.setNumParcelas(cursor.getInt(5));
				gasto.setIdGanho(cursor.getInt(6));
				gasto.setLocal(cursor.getString(7));
				gasto.setFoto(cursor.getString(8));

				list.add(gasto);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<GastoVO> selectAll() {
		
		List<GastoVO> list = new ArrayList<GastoVO>();
		
		String[] colunas = new String[]{"id", "descricao", "valor", "data", "parcela", "num_parcelas", "id_ganho", "local", "foto"};
		
		Cursor cursor = this.database.query(TABLE_GASTOS, colunas, null, null,null, null, "codigo");
		
		if (cursor.moveToFirst()) {
			do {
				GastoVO gasto = new GastoVO();
				gasto.setId(cursor.getInt(0));
				gasto.setDescricao(cursor.getString(1));
				gasto.setValor(cursor.getDouble(2));
				gasto.setData(cursor.getString(3));
				gasto.setParcela(cursor.getInt(4));
				gasto.setNumParcelas(cursor.getInt(5));
				gasto.setIdGanho(cursor.getInt(6));
				gasto.setLocal(cursor.getString(7));
				gasto.setFoto(cursor.getString(8));

				list.add(gasto);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

}
