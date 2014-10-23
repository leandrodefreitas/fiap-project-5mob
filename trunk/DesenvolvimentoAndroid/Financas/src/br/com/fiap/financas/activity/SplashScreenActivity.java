package br.com.fiap.financas.activity;

import br.com.fiap.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreenActivity extends Activity implements Runnable {

	private final int splashTime = 5000;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.splashscreen);
		
		Handler h = new Handler();
		h.postDelayed(this, splashTime);
	}
	
	public void run() {
		
		Intent i = new Intent();
		i.setClass(SplashScreenActivity.this, LoginActivity.class);
		startActivity(i);

		finish();
	}

}
