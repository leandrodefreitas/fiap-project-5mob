package br.com.fiap.minichef.common.dao;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;
import br.com.fiap.minichef.common.vo.ItemIngredienteVO;

public class ItemIngredienteDAO extends DataSource{

	private static final String INSERT = "insert into " 
			+ TABLE_ITEM_INGREDIENTES + " (id_receita, id_ingrediente, tipo, quantidade, unidadeMedida) values (?, ?, ?, ?, ?) ";
	
	private static final String SELECT_ALL = "select id, id_receita, id_ingrediente, tipo, quantidade, unidadeMedida from " 
			+ TABLE_ITEM_INGREDIENTES + " order by id";

	private static final String SELECT_BY_ID_RECEITA_TIPO = "select id, id_receita, id_ingrediente, tipo, quantidade, unidadeMedida from " 
			+ TABLE_ITEM_INGREDIENTES + " where id_receita = ? and tipo = ?";
	
	private static final String SELECT_BY_ID_INGREDIENTE_TIPO = "select id, id_receita, id_ingrediente, tipo, quantidade, unidadeMedida from " 
			+ TABLE_ITEM_INGREDIENTES + " where id_ingrediente = ? and tipo = ?";
	
	private SQLiteStatement insertStmt;
	
	public ItemIngredienteDAO(Context context) {
		super(context);
	}
	
	public long insert(ItemIngredienteVO vo){
		this.insertStmt = super.database.compileStatement(INSERT);
		this.insertStmt.bindLong(1, vo.getIdReceita());
		this.insertStmt.bindLong(2, vo.getIdIngrediente());
		this.insertStmt.bindLong(3, vo.getTipo());
		this.insertStmt.bindDouble(4, vo.getQuantidade());
		this.insertStmt.bindString(5, vo.getUnidadeMedida());
		return this.insertStmt.executeInsert();
	}
	
	public long delete(ItemIngredienteVO vo) {
		return 0;
	}
	
	public void deleteAll() {
		super.database.delete(TABLE_ITEM_INGREDIENTES, null, null);
	}		
	
	public List<ItemIngredienteVO> selectAll() {

		List<ItemIngredienteVO> list = new ArrayList<ItemIngredienteVO>();

		String[] args = {""};
		Cursor cursor = database.rawQuery(SELECT_ALL, args);

		if (cursor.moveToFirst()) {
			do {
				ItemIngredienteVO itemIngrediente = new ItemIngredienteVO();
				itemIngrediente.setId(cursor.getInt(0));
				itemIngrediente.setIdReceita(cursor.getInt(1));
				itemIngrediente.setIdIngrediente(cursor.getInt(2));
				itemIngrediente.setTipo(cursor.getInt(3));
				itemIngrediente.setQuantidade(cursor.getDouble(4));
				itemIngrediente.setUnidadeMedida(cursor.getString(5));
				list.add(itemIngrediente);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<ItemIngredienteVO> selectByIdTipo(Integer id, Integer tipo) {

		List<ItemIngredienteVO> list = new ArrayList<ItemIngredienteVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_RECEITA_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				ItemIngredienteVO itemIngrediente = new ItemIngredienteVO();
				itemIngrediente.setId(cursor.getInt(0));
				itemIngrediente.setIdReceita(cursor.getInt(1));
				itemIngrediente.setIdIngrediente(cursor.getInt(2));
				itemIngrediente.setTipo(cursor.getInt(3));
				itemIngrediente.setQuantidade(cursor.getDouble(4));
				itemIngrediente.setUnidadeMedida(cursor.getString(5));
				list.add(itemIngrediente);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}
	
	public List<ItemIngredienteVO> selectByIdIngrediente(Integer id, Integer tipo) {

		List<ItemIngredienteVO> list = new ArrayList<ItemIngredienteVO>();

		String[] args = {String.valueOf(id), String.valueOf(tipo)};
		Cursor cursor = database.rawQuery(SELECT_BY_ID_INGREDIENTE_TIPO, args);

		if (cursor.moveToFirst()) {
			do {
				ItemIngredienteVO itemIngrediente = new ItemIngredienteVO();
				itemIngrediente.setId(cursor.getInt(0));
				itemIngrediente.setIdReceita(cursor.getInt(1));
				itemIngrediente.setIdIngrediente(cursor.getInt(2));
				itemIngrediente.setTipo(cursor.getInt(3));
				itemIngrediente.setQuantidade(cursor.getDouble(4));
				itemIngrediente.setUnidadeMedida(cursor.getString(5));
				list.add(itemIngrediente);

			} while (cursor.moveToNext());
		}
		if (cursor != null && !cursor.isClosed()) {
			cursor.close();
		}
		return list;
	}	
		
	
}
