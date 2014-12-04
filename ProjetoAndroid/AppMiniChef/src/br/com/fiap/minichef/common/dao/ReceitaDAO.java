package br.com.fiap.minichef.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

public class ReceitaDAO extends DataSource {

	private static final String INSERT = "insert into "
			+ TABLE_RECEITAS
			+ " (nome, descricao, data, tempo, nota, categoria, foto) values (?, ?, ?, ?, ?, ?, ? )";
	
	
	private static final String SELECT_ALL_BY_DATA = "select id, nome, descricao, data, tempo, nota, categoria, foto from "
			+ TABLE_RECEITAS + " order by data desc";
	
	private static final String SELECT_BY_ID = "select id, nome, descricao, data, tempo, nota, categoria, foto from "
			+ TABLE_RECEITAS + " where id = ?";
	
	private static final String SELECT_MAX_ID = "select max(id) from " + TABLE_RECEITAS;	
	
	private static final String SELECT_BY_DATA = "select id, nome, descricao, data, tempo, nota, categoria, foto from "
			+ TABLE_RECEITAS + " where data = ? order by id";
	
	private static final String SELECT_BY_MES_ANO = "select id, nome, descricao, data, tempo, nota, categoria, foto from "
			+ TABLE_RECEITAS + " where substr(data,6,2) = ? and substr(data,1,4) = ? order by id";
	
	private static final String SELECT_BY_NOME = "select id, nome, descricao, data, tempo, nota, categoria, foto from "
			+ TABLE_RECEITAS + " where nome = ?";
	
	private SQLiteStatement insertStmt;

	public ReceitaDAO(Context context) {
		super(context);
	}

	public long insert(ReceitaVO vo) {
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindString(1, vo.getNome());
		this.insertStmt.bindString(2, vo.getDescricao());
		this.insertStmt.bindString(3, vo.getDataFormatted());
		this.insertStmt.bindLong(4, vo.getTempo());
		this.insertStmt.bindLong(5, vo.getNota());
		this.insertStmt.bindString(6, vo.getCategoria());
		this.insertStmt.bindString(7, vo.getFoto());
		return this.insertStmt.executeInsert();
	}

	public long delete(ReceitaVO vo) {
		return 0;
	}

	public void deleteAll() {
		super.database.delete(TABLE_RECEITAS, null, null);
	}

	public List<ReceitaVO> selectAll() {
		
		List<ReceitaVO> list = new ArrayList<ReceitaVO>();
		
		/*Cursor cursor = database.rawQuery(SELECT_ALL, null);*/
		
		Cursor cursor = database.rawQuery(SELECT_ALL_BY_DATA, null);
		
		if (cursor.moveToFirst()) {
			do {
				ReceitaVO receita = new ReceitaVO();
				receita.setId(cursor.getInt(0));
				receita.setNome(cursor.getString(1));
				receita.setDescricao(cursor.getString(2));
				receita.setData(cursor.getString(3));
				receita.setTempo(cursor.getInt(4));
				receita.setNota(cursor.getInt(5));
				receita.setCategoria(cursor.getString(6));
				receita.setFoto(cursor.getString(7));
				
				ReceitaSCN receitaSCN = new ReceitaSCN(context);
				receita.setIngredientes(receitaSCN.obterIngredientesPorId(cursor.getInt(0)));				

				list.add(receita);
				
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
	
	
	public List<ReceitaVO> selectByData(String data) {
		
		List<ReceitaVO> list = new ArrayList<ReceitaVO>();

		String[] args = new String[]{data};
		
		Cursor cursor = database.rawQuery(SELECT_BY_DATA, args);
	
		if (cursor.moveToFirst()) {
			do {
				ReceitaVO receita = new ReceitaVO();
				receita.setId(cursor.getInt(0));
				receita.setNome(cursor.getString(1));
				receita.setDescricao(cursor.getString(2));
				receita.setData(cursor.getString(3));
				receita.setTempo(cursor.getInt(4));
				receita.setNota(cursor.getInt(5));
				receita.setCategoria(cursor.getString(6));
				receita.setFoto(cursor.getString(7));
				
				
				ReceitaSCN receitaSCN = new ReceitaSCN(context);
				receita.setIngredientes(receitaSCN.obterIngredientesPorId(cursor.getInt(0)));				

				list.add(receita);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public ReceitaVO selectById(int id) {
		
		ReceitaVO receita = new ReceitaVO();

		String[] args = new String[] { String.valueOf(id) };
		Cursor cursor = database.rawQuery(SELECT_BY_ID, args);
	
		if (cursor.moveToFirst()) {
			do {
				receita.setId(cursor.getInt(0));
				receita.setNome(cursor.getString(1));
				receita.setDescricao(cursor.getString(2));
				receita.setData(cursor.getString(3));
				receita.setTempo(cursor.getInt(4));
				receita.setNota(cursor.getInt(5));
				receita.setCategoria(cursor.getString(6));
				receita.setFoto(cursor.getString(7));
				
				
				ReceitaSCN receitaSCN = new ReceitaSCN(context);
				receita.setIngredientes(receitaSCN.obterIngredientesPorId(cursor.getInt(0)));
				
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return receita;
	}		
	
	public List<ReceitaVO> selectByMesAno(String mes, String ano) {
		
		List<ReceitaVO> list = new ArrayList<ReceitaVO>();

		String[] args = new String[]{mes, ano};
		Cursor cursor = database.rawQuery(SELECT_BY_MES_ANO, args);
		
		if (cursor.moveToFirst()) {
			do {
				ReceitaVO receita = new ReceitaVO();
				receita.setId(cursor.getInt(0));
				receita.setNome(cursor.getString(1));
				receita.setDescricao(cursor.getString(2));
				receita.setData(cursor.getString(3));
				receita.setTempo(cursor.getInt(4));
				receita.setNota(cursor.getInt(5));
				receita.setCategoria(cursor.getString(6));
				receita.setFoto(cursor.getString(7));
				
				
				ReceitaSCN receitaSCN = new ReceitaSCN(context);
				receita.setIngredientes(receitaSCN.obterIngredientesPorId(cursor.getInt(0)));				

				list.add(receita);
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public ReceitaVO selectByNome(String nome) {
		ReceitaVO receita = new ReceitaVO();

		String[] args = new String[] { String.valueOf(nome) };
		Cursor cursor = database.rawQuery(SELECT_BY_NOME, args);

		if (cursor.moveToFirst()) {
			do {
				receita.setId(cursor.getInt(0));
				receita.setNome(cursor.getString(1));
				receita.setDescricao(cursor.getString(2));
				receita.setData(cursor.getString(3));
				receita.setTempo(cursor.getInt(4));
				receita.setNota(cursor.getInt(5));
				receita.setCategoria(cursor.getString(6));
				receita.setFoto(cursor.getString(7));
				
				
				ReceitaSCN receitaSCN = new ReceitaSCN(context);
				receita.setIngredientes(receitaSCN.obterIngredientesPorId(cursor.getInt(0)));
				
				
			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return receita;
	}
	
	public Boolean checksForByNome(String nome) {
		Boolean retorno = true;
		String[] args = new String[] { String.valueOf(nome) };
		Cursor cursor = database.rawQuery(SELECT_BY_NOME, args);
		if (!cursor.moveToFirst()) {
			retorno = false;
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return retorno;
	}	

}
