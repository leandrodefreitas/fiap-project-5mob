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
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
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
		trace("Finanças");
		Intent i = new Intent(DashboardActivity.this, FinancasFragActivity.class);
		startActivity(i);
	}
	
	public void onClickGraficos(View v) {
		trace("Gráficos");
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
    	.setNegativeButton("Não", null)
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
        	.setNegativeButton("Não", null)
        	.show();
		
	}
	
	private AlertDialog alerta;

	public void onClickInfo(View v) {
		/*Intent i = new Intent(DashboardActivity.this, InformacoesActivity.class);
		startActivity(i);*/
		
		/*new AlertDialog.Builder(this)
    	.setTitle("Informações")
    	.setMessage("Desenvolvedores: \n \n Flavio Ota \n Leandro de Freitas \n Rodrigo Ota \n Wellington Santos")
    	.setIcon(R.drawable.ic_informacoes)
    	.setNegativeButton("Voltar", null)
    	.show();*/
		
		//LayoutInflater é utilizado para inflar nosso layout em uma view. 
		//-pegamos nossa instancia da classe 
		LayoutInflater li = getLayoutInflater(); 
		//inflamos o layout LAYOUT na view 
		View view = li.inflate(R.layout.informacoes, null); 
		//definimos para o botão do layout um clickListener 
		view.findViewById(R.id.btVoltar).setOnClickListener(new View.OnClickListener() { 
			public void onClick(View arg0) { alerta.dismiss(); } 
		}); 
		AlertDialog.Builder builder = new AlertDialog.Builder(this); 
		builder.setTitle("Informações");
		builder.setIcon(R.drawable.ic_informacoes);
		builder.setView(view); 
		alerta = builder.create(); 
		alerta.show();
	
	}
	
	public void onClickCalendario(View v) {
		Intent i = new Intent(DashboardActivity.this, CalendarActivity.class);
		startActivity(i);
	}
	
	private void trace(String msg) {
		Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
	}
	
}