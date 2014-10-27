package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.RegistroVO;

public class RegistroDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_REGISTROS
			+ " (codigo, tipo, descricao, valor, data, parcela, num_parcelas, local, foto) values ( ?, ?, ?, ?, ?, ?, ?, ?, ? )";
	private static final String SELECT_ALL = "select codigo, tipo, descricao, valor, data, parcela, num_parcelas, local, foto from "
			+ TABLE_REGISTROS;
	private static final String SELECT_BY_DATA = "select codigo, tipo, descricao, valor, data, parcela, num_parcelas, local, foto from "
			+ TABLE_REGISTROS + " order by data ASC";

	private static final String SELECT_BY_TIPO = "select codigo, tipo, descricao, valor, data, parcela, num_parcelas, local, foto from "
			+ TABLE_REGISTROS + " where tipo = ";

	private SQLiteStatement insertStmt;

	public RegistroDAO(Context context) {
		super(context);
	}

	public long insert(RegistroVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindLong(1, vo.getTipo().ordinal());
		this.insertStmt.bindString(2, vo.getDescricao());
		this.insertStmt.bindDouble(3, vo.getValor());
		//this.insertStmt.bindString(4, vo.getDataFormatted());
		//this.insertStmt.bindLong(5, vo.getParcela());
		//this.insertStmt.bindLong(6, vo.getNumParcelas());
		//this.insertStmt.bindString(7, vo.getLocal());
		//this.insertStmt.bindString(8, vo.getFoto());
		return this.insertStmt.executeInsert();
	}

	public long delete(RegistroVO vo) {
		return 0;
	}

	public void deleteAll() {
		super.database.delete(TABLE_REGISTROS, null, null);
	}
	
	public List<RegistroVO> selectByType(int tipo) {
		
		List<RegistroVO> list = new ArrayList<RegistroVO>();
		
		String[] colunas = new String[]{"codigo", "tipo", "descricao", "valor", "data", "parcela", "num_parcelas", "local", "foto"};
		String[ ] args = new String[ ]
		{ String.valueOf(tipo) } ;
		
		Cursor cursor = this.database.query(TABLE_REGISTROS, colunas, "tipo=?", args, null, null, "codigo");
		
		if (cursor.moveToFirst()) {
			do {
				RegistroVO registro = new RegistroVO();
				registro.setCodigo(cursor.getInt(0));
				registro.tipo(cursor.getInt(1));
				registro.setDescricao(cursor.getString(2));
				registro.setValor(cursor.getDouble(3));
				registro.setData(cursor.getString(4));
				registro.setParcela(cursor.getInt(5));
				registro.setNumParcelas(cursor.getInt(6));
				registro.setLocal(cursor.getString(7));
				registro.setFoto(cursor.getString(8));

				list.add(registro);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	public List<RegistroVO> selectAll() {
		
		List<RegistroVO> list = new ArrayList<RegistroVO>();
		
		String[] colunas = new String[]{"codigo", "tipo", "descricao", "valor", "data", "parcela", "num_parcelas", "local", "foto"};
		
		Cursor cursor = this.database.query(TABLE_REGISTROS, colunas, null, null,null, null, "codigo");
		
		if (cursor.moveToFirst()) {
			do {
				RegistroVO registro = new RegistroVO();
				registro.setCodigo(cursor.getInt(0));
				registro.tipo(cursor.getInt(1));
				registro.setDescricao(cursor.getString(2));
				registro.setValor(cursor.getDouble(3));
				registro.setData(cursor.getString(4));
				registro.setParcela(cursor.getInt(5));
				registro.setNumParcelas(cursor.getInt(6));
				registro.setLocal(cursor.getString(7));
				registro.setFoto(cursor.getString(8));

				list.add(registro);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

}
