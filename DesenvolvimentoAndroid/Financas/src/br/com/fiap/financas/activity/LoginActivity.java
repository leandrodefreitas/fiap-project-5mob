package br.com.fiap.financas.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import br.com.fiap.R;

public class LoginActivity extends Activity {

	Button btEntrar;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);
		/*
		btEntrar = (Button) findViewById(R.id.btEntrar);
		
		btEntrar.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				
				Log.i("Click", "Clicou botão entrar login");
				
				//finish();
				
				Intent i = new Intent(LoginActivity.this,DashboardActivity.class);
				startActivity(i);				
			}
		});*/
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
		
		Log.i("Click", "Clicou botão entrar login");
		
		//finish();
		
		Intent i = new Intent(LoginActivity.this, DashboardActivity.class);
		startActivity(i);
	}

}
