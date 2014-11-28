package br.com.fiap.minichef.common.dao;

import java.io.File;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;

public class DataSource extends SQLiteOpenHelper {
	protected Context context;
	protected SQLiteDatabase database;

	public static String DATABASE_COMPLETE = "";
	public static final String DATABASE_NAME = "minichef.db";
	public static final String DATABASE_NAME_JOURNAL = "minichef.db-journal";
	public static final int DATABASE_VERSION = 3;
	
	protected static final String TABLE_RECEITAS = "RECEITAS";
	protected static final String TABLE_INGREDIENTES = "INGREDIENTES";
	protected static final String TABLE_ITEM_INGREDIENTES = "ITEM_INGREDIENTES";
	protected static final String TABLE_CATEGORIAS = "CATEGORIAS";
	protected static final String TABLE_RECEITA_CATEGORIA = "RECEITA_CATEGORIA";

	static {
		if (br.com.fiap.minichef.util.Environment.DEVELOPMENT) {
			DATABASE_COMPLETE = DATABASE_NAME;
		} else {
			DATABASE_COMPLETE = Environment.getExternalStorageDirectory().getPath()
					+ File.separator + DATABASE_NAME;
		}
	}

	public boolean createAllTables() {
		return false;
	}

	public boolean cleanUp() {
		return false;
	}

	public DataSource(Context context) {
		super(context, DATABASE_COMPLETE, null, DATABASE_VERSION);
		this.context  = context;
		this.database = context.openOrCreateDatabase(DATABASE_COMPLETE, Context.MODE_PRIVATE, null);
		this.database = getWritableDatabase();
	}

	public void onCreate(SQLiteDatabase db) {
		
		db.execSQL( "CREATE TABLE "
				+ TABLE_INGREDIENTES
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " 
				+ "descricao TEXT NOT NULL )" );
		
		db.execSQL( "CREATE TABLE "
				+ TABLE_RECEITAS
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ "nome TEXT NOT NULL, "
				+ "descricao TEXT NOT NULL, "
				+ "valor DOUBLE NOT NULL, "
				+ "data TEXT NOT NULL, "
				+ "tempo INTEGER, "
				+ "nota INTEGER NOT NULL, "
				+ "categoria TEXT, "
				+ "foto TEXT )");
		
		db.execSQL( "CREATE TABLE "
				+ TABLE_ITEM_INGREDIENTES
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ "id_receita INTEGER, "
				+ "id_ingrediente INTEGER, "
				+ "tipo INTEGER NOT NULL, " 
				+ "quantidade INTEGER NOT NULL, " 
				+ "unidadeMedida TEXT NOT NULL )");
		
		db.execSQL( "CREATE TABLE "
				+ TABLE_CATEGORIAS
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " 
				+ "descricao TEXT NOT NULL )" );
		
		db.execSQL( "CREATE TABLE "
				+ TABLE_RECEITA_CATEGORIA
				+ " (id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, "
				+ "id_receita INTEGER, "
				+ "id_categoria INTEGER, "
				+ "tipo INTEGER NOT NULL )");
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_INGREDIENTES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEITAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM_INGREDIENTES);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_RECEITA_CATEGORIA);
		onCreate(db);
	}

	public void close() {
		this.database.close();
	}
	
}