package br.com.fiap.financas.activity;

import br.com.fiap.R;
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
	}

	public void onClickFinancas(View v) {
		trace("Finanças");
	}
	
	public void onClickGraficos(View v) {
		trace("Gráficos");
	}

	public void onClickBackup(View v) {
		trace("Backup") ;
	}
	
	public void onClickExcluirDados() {
		new AlertDialog.Builder(this)
	    	.setMessage("Você deseja realmente excluir todos os registros?")
	        .setCancelable(false)
	        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					// TODO Auto-generated method stub
					
				}
	        	//public void onClick(DialogInterface dialog, int id) {
	        		//DashboardActivity.this.finish();
	        		//trace("Excluir dados");
	            //}	
	        })
	        .setNegativeButton("Não", null)
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