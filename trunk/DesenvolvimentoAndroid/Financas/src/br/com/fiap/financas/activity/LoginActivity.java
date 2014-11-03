package br.com.fiap.financas.activity;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import br.com.fiap.R;

public class LoginActivity extends Activity {
	public static final String PREFS_NAME = "Login";
	Button btEntrar;
	EditText edtUsuario;
	EditText edtSenha;
	CheckBox checkLembrar;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		if (settings.getBoolean("Lembrar", false) != false) {
			edtUsuario = (EditText) findViewById(R.id.etUsuario);
			edtSenha = (EditText) findViewById(R.id.etSenhA);
			checkLembrar = (CheckBox) findViewById(R.id.chkLembrar);
			edtUsuario.setText(settings.getString("Usuario", ""));
			edtSenha.setText(settings.getString("Senha", ""));
			checkLembrar.setChecked(settings.getBoolean("Lembrar", false));
		}
		
		if (settings.getBoolean("Bloqueado", false) == true) {
			
			Date dtHoraDesbloqueio = null;
			Calendar clHoraDesbloqueio = new GregorianCalendar();
			Calendar clHoraAtual = new GregorianCalendar();
			
			String horaBloqueio = settings.getString("HoraBloqueio", "");
			
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			try {
				dtHoraDesbloqueio = sdf.parse(horaBloqueio);
			}
			catch (Exception e) {
				Log.e("erro", e.getMessage());
			}
			
			clHoraDesbloqueio.setTime(dtHoraDesbloqueio);
			
			//TODO Minute para teste, depois alterar para clHoraDesbloqueio.add(Calendar.HOUR, 1);
			clHoraDesbloqueio.add(Calendar.MINUTE, 1);
			
			// se hora atual posterior hora bloqueio + 1 (desbloqueio)
			if (clHoraAtual.getTime().after(clHoraDesbloqueio.getTime())) {
				
				editor.putBoolean("Bloqueado", false);
				editor.putInt("Tentativa", 0);
				editor.commit();
				
			}
			
			
			dtHoraDesbloqueio = clHoraDesbloqueio.getTime();
			String horaDepois = sdf.format(dtHoraDesbloqueio);
			Log.i("Bloqueio", "Hora do bloqueio: " + horaBloqueio);			
			Log.i("Bloqueio", "Hora do desbloqueio: " + horaDepois);
			
		}
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	

	public void onClickEntrar(View v) {
		
		Log.i("Click", "Clicou botao entrar login");
		
		Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
		edtUsuario = (EditText) findViewById(R.id.etUsuario);
		edtSenha = (EditText) findViewById(R.id.etSenhA);
		SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
		SharedPreferences.Editor editor = settings.edit();
		
		if (settings.getString("FirstLogin", "") != null && !(settings.getString("FirstLogin", "").equals(""))) {
			if(edtUsuario.getText().toString().equals(settings.getString("Usuario", "")) && edtSenha.getText().toString().equals(settings.getString("Senha", "")) && !settings.getBoolean("Bloqueado", false)){
				startActivity(i);
			} else if (settings.getBoolean("Bloqueado", false)) {
				alertaLogin(0);
			} else { 
				Integer tentativa = settings.getInt("Tentativa", 0);
				tentativa++;
				editor.putInt("Tentativa", tentativa);
				if (tentativa >= 3) {
					editor.putBoolean("Bloqueado", true);	
					String horaBloqueio = new SimpleDateFormat("dd/MM/yyyy HH:mm").format(new Date());
					Log.i("HoraBloqueio", horaBloqueio);
					editor.putString("HoraBloqueio", horaBloqueio);
					alertaLogin(0);
				} else		
					alertaLogin(1);
			}
			
		} else {
			editor.putString("FirstLogin", "N");
			editor.putString("Usuario", edtUsuario.getText().toString());
			editor.putString("Senha", edtSenha.getText().toString());
			editor.putInt("Tentativa", 0);
			editor.putBoolean("Bloqueado", false);
			
			startActivity(i);
		}
		
		checkLembrar = (CheckBox) findViewById(R.id.chkLembrar);
		editor.putBoolean("Lembrar", checkLembrar.isChecked());
		editor.commit();

	}
	
	private void alertaLogin(Integer tipo) {
		AlertDialog alerta = null;
		
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle("Erro no Login");
		
		if(tipo == 0){
			builder.setMessage("Login Bloqueado");
		} else {
			builder.setMessage("Erro no usuario ou senha"); 	 
		}
		
		builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
			}
		});
		alerta = builder.create();
        alerta.show();
		

	}

}
