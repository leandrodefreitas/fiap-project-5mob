package br.com.fiap.financas.common.dao ;

import java.io.File ;

import android.content.Context ;
import android.database.sqlite.SQLiteDatabase ;
import android.database.sqlite.SQLiteOpenHelper ;
import android.os.Environment ;

public class DataSource extends SQLiteOpenHelper {
	protected Context context;
	protected SQLiteDatabase database;

	public static String DATABASE_COMPLETE = "";
	public static final String DATABASE_NAME = "financas.db";
	public static final String DATABASE_NAME_JOURNAL = "financas.db-journal";
	public static final int DATABASE_VERSION = 1 ;

	protected static final String TABLE_REGISTROS = "REGISTROS";
	protected static final String TABLE_CATEGORIAS = "CATEGORIAS";
	protected static final String TABLE_REGISTRO_CATEGORIA = "REGISTRO_CATEGORIA";

	static {
		if (br.com.fiap.financas.util.Environment.DEVELOPMENT) {
			DATABASE_COMPLETE = DATABASE_NAME ;
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
				+ TABLE_REGISTROS
				+ " (codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, tipo INTEGER, descricao TEXT, valor DOUBLE, data TEXT, parcela INTEGER, num_parcelas INTEGER, local TEXT, foto TEXT )");
		db.execSQL( "CREATE TABLE "
				+ TABLE_CATEGORIAS
				+ " (codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, descricao TEXT )" );
		db.execSQL( "CREATE TABLE "
				+ TABLE_REGISTRO_CATEGORIA
				+ " (codigo INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, cod_reg INTEGER, cod_cat INTEGER )" );
	}

	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTROS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEGORIAS);
		db.execSQL("DROP TABLE IF EXISTS " + TABLE_REGISTRO_CATEGORIA);
		onCreate(db);
	}

	public void close() {
		this.database.close();
	}
}