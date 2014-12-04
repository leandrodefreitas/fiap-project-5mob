package br.com.fiap.minichef.activity ;

import br.com.fiap.minichef.util.PersistenciaAsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

public class Splash extends Activity implements Runnable {
	
	private final int splashTime = 3000;
	//private final String applicationId = "vZRcNsJgJTUArSJx6lEOIHa336uIOdC2Bvv3N05J";
	//private final String javascriptKey = "ootFWZmzhMf6amSRY0mGbgNU6Htcm7FnCOrrL1et";
	//private String URL = "https://" + applicationId + ":javascript-key=" + javascriptKey + "@api.parse.com/1/classes/RECEITAS";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//requestWindowFeature(Window.FEATURE_NO_TITLE);
		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.splash);
		
		Handler h = new Handler();
		h.postDelayed(this, splashTime);
	}

	public void run() {
		/*
		PersistenciaAsyncTask task = new PersistenciaAsyncTask(this);
		if (isConnected()){
			task.execute();
			startActivity(new Intent(".MenuActivity"));
			finish();
		} else {
			Toast.makeText(getBaseContext(), "Sem acesso à internet. Favor verificar.",
					Toast.LENGTH_LONG).show();
			finish();
		}
		*/
		startActivity(new Intent(".MenuActivity"));
		finish();		
		
	}
	
	// método para verificar conexão com a internet
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;           	
        } else {
            return false;              	
        }
    }
}