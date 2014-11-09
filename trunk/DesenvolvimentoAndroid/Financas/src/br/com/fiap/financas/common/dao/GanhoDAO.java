package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.services.scn.GastoSCN;

public class GanhoDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_GANHOS
			+ " (descricao, valor, data, parcela, num_parcelas) values (?, ?, ?, ?, ?)";
	
	private static final String SELECT_ALL_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " order by data desc";
	
	private static final String SELECT_BY_ID = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " where id = ?";
	
	private static final String SELECT_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " where data = ? order by id";

	private static final String SELECT_BY_MES_ANO = "select id, descricao, valor, data, parcela, num_parcelas from "
			+ TABLE_GANHOS + " where substr(data,6,2) = ? and substr(data,1,4) = ? order by id";

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

	public List<GanhoVO> selectAll() {

		List<GanhoVO> list = new ArrayList<GanhoVO>();

		/*Cursor cursor = database.rawQuery(SELECT_ALL, null);*/
		
		Cursor cursor = database.rawQuery(SELECT_ALL_BY_DATA, null);

		if (cursor.moveToFirst()) {
			do {
				GanhoVO ganho = new GanhoVO();
				ganho.setId(cursor.getInt(0));
				ganho.setDescricao(cursor.getString(1));
				ganho.setValor(cursor.getDouble(2));
				ganho.setData(cursor.getString(3));
				ganho.setParcela(cursor.getInt(4));
				ganho.setNumParcelas(cursor.getInt(5));
				
				GastoSCN gastoSCN = new GastoSCN(context);
				Double somaGastos = gastoSCN.obterSomaGastosPorGanho(ganho.getId());
				Double saldo = ganho.getValor() - somaGastos;
				ganho.setSaldo(saldo);				

				list.add(ganho);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}

	
	public GanhoVO selectById(int id) {

		GanhoVO ganho = new GanhoVO();
		
		String[] args = new String[] { String.valueOf(id) };
		Cursor cursor = database.rawQuery(SELECT_BY_ID, args);

		if (cursor.moveToFirst()) {
			do {
				
				ganho.setId(cursor.getInt(0));
				ganho.setDescricao(cursor.getString(1));
				ganho.setValor(cursor.getDouble(2));
				ganho.setData(cursor.getString(3));
				ganho.setParcela(cursor.getInt(4));
				ganho.setNumParcelas(cursor.getInt(5));
				
				GastoSCN gastoSCN = new GastoSCN(context);
				Double somaGastos = gastoSCN.obterSomaGastosPorGanho(ganho.getId());
				Double saldo = ganho.getValor() - somaGastos;
				ganho.setSaldo(saldo);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return ganho;
	}	
	
	
	public List<GanhoVO> selectByData(String data) {

		List<GanhoVO> list = new ArrayList<GanhoVO>();

		String[] args = new String[]{data};
		Cursor cursor = database.rawQuery(SELECT_BY_DATA, args);

		if (cursor.moveToFirst()) {
			do {
				GanhoVO ganho = new GanhoVO();
				ganho.setId(cursor.getInt(0));
				ganho.setDescricao(cursor.getString(1));
				ganho.setValor(cursor.getDouble(2));
				ganho.setData(cursor.getString(3));
				ganho.setParcela(cursor.getInt(4));
				ganho.setNumParcelas(cursor.getInt(5));
				
				GastoSCN gastoSCN = new GastoSCN(context);
				Double somaGastos = gastoSCN.obterSomaGastosPorGanho(ganho.getId());
				Double saldo = ganho.getValor() - somaGastos;
				ganho.setSaldo(saldo);				

				list.add(ganho);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}	
	
	public List<GanhoVO> selectByMesAno(String mes, String ano) {

		List<GanhoVO> list = new ArrayList<GanhoVO>();

		String[] args = new String[]{mes, ano};
		Cursor cursor = database.rawQuery(SELECT_BY_MES_ANO, args);

		if (cursor.moveToFirst()) {
			do {
				GanhoVO ganho = new GanhoVO();
				ganho.setId(cursor.getInt(0));
				ganho.setDescricao(cursor.getString(1));
				ganho.setValor(cursor.getDouble(2));
				ganho.setData(cursor.getString(3));
				ganho.setParcela(cursor.getInt(4));
				ganho.setNumParcelas(cursor.getInt(5));
				
				GastoSCN gastoSCN = new GastoSCN(context);
				Double somaGastos = gastoSCN.obterSomaGastosPorGanho(ganho.getId());
				Double saldo = ganho.getValor() - somaGastos;
				ganho.setSaldo(saldo);				

				list.add(ganho);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}	
		
	
}
