package br.com.fiap.financas.activity;

import br.com.fiap.R;
import android.app.Activity;
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

	public void onClickInfo(View v) {
		trace("Informações") ;
	}
	
	public void onClickCalendario(View v) {
		Intent i = new Intent(DashboardActivity.this, CalendarActivity.class);
		startActivity(i);
	}
	
	private void trace(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
}