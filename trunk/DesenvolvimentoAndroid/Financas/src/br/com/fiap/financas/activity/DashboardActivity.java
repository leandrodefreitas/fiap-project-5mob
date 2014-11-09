package br.com.fiap.financas.activity;

import java.io.File;
import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;
import br.com.fiap.R;
import br.com.fiap.financas.common.dao.DataSource;
import br.com.fiap.financas.util.Backup;

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
    	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			try {
    				
    				File databasePath = getApplicationContext().getDatabasePath(DataSource.DATABASE_NAME).getParentFile();
    				String backupPathString = (Environment.getExternalStorageDirectory( ).getPath( ) + File.separator + "Backup");
    				File backupPath = new File(backupPathString);
    				
    				Backup bkp = new Backup();
					bkp.copyDirectory(databasePath, backupPath);
					
					trace("Backup dos dados realizado");
					
				} catch (IOException e) {
					e.printStackTrace();
				}
    			
    		}
    	})
    	.setNegativeButton(getString(R.string.nao), null)
    	.show();
		
	}
	
	public void onClickExcluirDados(View v) {
		
		new AlertDialog.Builder(this)
        	.setTitle("Excluir registros!")
        	.setMessage("Deseja realmente excluir todos os registros?")
        	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			trace("Excluindo Registros!");    
        		}
        	})
        	.setNegativeButton(getString(R.string.nao), null)
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
		builder.setTitle(getString(R.string.informacoes));
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