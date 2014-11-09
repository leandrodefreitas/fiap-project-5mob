package br.com.fiap.financas.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;

public class GastoDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_GASTOS
			+ " (descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto) values (?, ?, ?, ?, ?, ?, ?, ? )";
	
	
	private static final String SELECT_ALL = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS;
	
	private static final String SELECT_ALL_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS + " order by data desc";
	
	private static final String SELECT_MAX_ID = "select max(id) from " + TABLE_GASTOS;	
	
	private static final String SELECT_BY_DATA = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS + " where data = ? order by id";
	
	private static final String SELECT_BY_MES_ANO = "select id, descricao, valor, data, parcela, num_parcelas, id_ganho, local, foto from "
			+ TABLE_GASTOS + " where substr(data,6,2) = ? and substr(data,1,4) = ? order by id";
	
	private static final String SELECT_SUM_BY_GANHO = "select sum(valor) from " + TABLE_GASTOS + " where id_ganho = ? order by id";
	
	
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
		this.insertStmt.bindLong(6, vo.getGanhoDescontar().getId());
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

	public List<GastoVO> selectAll() {
		
		List<GastoVO> list = new ArrayList<GastoVO>();
		
		/*Cursor cursor = database.rawQuery(SELECT_ALL, null);*/
		
		Cursor cursor = database.rawQuery(SELECT_ALL_BY_DATA, null);
		
		if (cursor.moveToFirst()) {
			do {
				GastoVO gasto = new GastoVO();
				gasto.setId(cursor.getInt(0));
				gasto.setDescricao(cursor.getString(1));
				gasto.setValor(cursor.getDouble(2));
				gasto.setData(cursor.getString(3));
				gasto.setParcela(cursor.getInt(4));
				gasto.setNumParcelas(cursor.getInt(5));
				
				GanhoVO ganho = new GanhoVO();
				GanhoSCN ganhoSCN = new GanhoSCN(context);
				ganho = ganhoSCN.obterGanhoPorId(cursor.getInt(6));
				gasto.setGanhoDescontar(ganho);
				
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

	
	public Integer selectMaxId(){
		
		Integer maxId = 0;
		
		Cursor cursor = database.rawQuery(SELECT_MAX_ID, null);		
		
		if (cursor.moveToFirst()) {
			maxId = cursor.getInt(0);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return maxId;
	}
	
	
	public List<GastoVO> selectByData(String data) {
		
		List<GastoVO> list = new ArrayList<GastoVO>();

		String[] args = new String[]{data};
		
		Cursor cursor = database.rawQuery(SELECT_BY_DATA, args);
	
		if (cursor.moveToFirst()) {
			do {
				GastoVO gasto = new GastoVO();
				gasto.setId(cursor.getInt(0));
				gasto.setDescricao(cursor.getString(1));
				gasto.setValor(cursor.getDouble(2));
				gasto.setData(cursor.getString(3));
				gasto.setParcela(cursor.getInt(4));
				gasto.setNumParcelas(cursor.getInt(5));
				
				GanhoVO ganho = new GanhoVO();
				GanhoSCN ganhoSCN = new GanhoSCN(context);
				ganho = ganhoSCN.obterGanhoPorId(cursor.getInt(6));
				gasto.setGanhoDescontar(ganho);
				
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
	
	
	public List<GastoVO> selectByMesAno(String mes, String ano) {
		
		List<GastoVO> list = new ArrayList<GastoVO>();

		String[] args = new String[]{mes, ano};
		Cursor cursor = database.rawQuery(SELECT_BY_MES_ANO, args);
		
		if (cursor.moveToFirst()) {
			do {
				GastoVO gasto = new GastoVO();
				gasto.setId(cursor.getInt(0));
				gasto.setDescricao(cursor.getString(1));
				gasto.setValor(cursor.getDouble(2));
				gasto.setData(cursor.getString(3));
				gasto.setParcela(cursor.getInt(4));
				gasto.setNumParcelas(cursor.getInt(5));
				
				GanhoVO ganho = new GanhoVO();
				GanhoSCN ganhoSCN = new GanhoSCN(context);
				ganho = ganhoSCN.obterGanhoPorId(cursor.getInt(6));
				gasto.setGanhoDescontar(ganho);
				
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
	
	public Double selectSumGastosByGanho(Integer idGanho) {
		
		Double somaValor = 0.0;
		
		String[] args = new String[]{String.valueOf(idGanho)};
		Cursor cursor = database.rawQuery(SELECT_SUM_BY_GANHO, args);	
		
		if (cursor.moveToFirst()) {
			somaValor = cursor.getDouble(0);
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}		
		return somaValor;
	}	

}
