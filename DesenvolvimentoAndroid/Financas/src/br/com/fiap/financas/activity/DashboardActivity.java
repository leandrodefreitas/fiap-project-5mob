package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.Date;

import br.com.fiap.R;
import br.com.fiap.financas.common.dao.CategoriaDAO;
import br.com.fiap.financas.common.dao.RegistroDAO;
import br.com.fiap.financas.common.vo.CategoriaVO;
import br.com.fiap.financas.common.vo.RegistroVO;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class DashboardActivity extends Activity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.dashboard);
		
	}

	public void onClickCategorias(View v) {
		trace("Categorias");
		Intent i = new Intent(DashboardActivity.this, CategoriasActivity.class);
		startActivity(i);
	}

	public void onClickFinancas(View v) {
		trace("Finan�as");
		Intent i = new Intent(DashboardActivity.this, FinancasFragActivity.class);
		startActivity(i);
	}
	
	public void onClickGraficos(View v) {
		trace("Gr�ficos");
		Intent i = new Intent(DashboardActivity.this, GraficosActivity.class);
		startActivity(i);
	}

	public void onClickBackup(View v) {
		
		new AlertDialog.Builder(this)
    	.setTitle("Backup")
    	.setMessage("Deseja realmente realizar o backup?")
    	.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			trace("Backup");   
    		}
    	})
    	.setNegativeButton("N�o", null)
    	.show();
		
	}
	
	public void onClickExcluirDados(View v) {
		
		new AlertDialog.Builder(this)
        	.setTitle("Excluir registros!")
        	.setMessage("Deseja realmente excluir todos os registros?")
        	.setPositiveButton("Sim", new DialogInterface.OnClickListener() {
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			trace("Excluindo Registros!");    
        		}
        	})
        	.setNegativeButton("N�o", null)
        	.show();
		
	}

	public void onClickInfo(View v) {
		Intent i = new Intent(DashboardActivity.this, InformacoesActivity.class);
		startActivity(i);
	}
	
	public void onClickCalendario(View v) {
		Intent i = new Intent(DashboardActivity.this, CalendarActivity.class);
		startActivity(i);
	}
	
	private void trace(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
}