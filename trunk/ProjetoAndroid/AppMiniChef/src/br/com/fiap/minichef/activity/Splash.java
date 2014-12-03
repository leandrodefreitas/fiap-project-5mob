package br.com.fiap.minichef.activity ;

import br.com.fiap.minichef.util.PersistenciaAsyncTask;
import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;
import com.parse.*;

public class Splash extends Activity implements Runnable {
	
	private final int splashTime = 3000;
	private final String applicationId = "vZRcNsJgJTUArSJx6lEOIHa336uIOdC2Bvv3N05J";
	private final String clientKey = "fc2okWxf4ZFb8ck26sHLOKiadR36Q0i7V0lFp1ZQ";
	private final String javascriptKey = "ootFWZmzhMf6amSRY0mGbgNU6Htcm7FnCOrrL1et";
	private String URL = "https://" + applicationId + ":javascript-key=" + javascriptKey + "@api.parse.com/1/classes/RECEITAS";

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
		/*Parse.enableLocalDatastore(getApplicationContext());
		Parse.initialize(this, applicationId, clientKey);
		ParseObject RECEITAS = new  ParseObject ("RECEITAS");*/
		
		Uri uri = Uri.parse(URL);
		
		PersistenciaAsyncTask task = new PersistenciaAsyncTask(this);
		if (isConnected()){
			task.execute(URL);
			startActivity(new Intent(".MenuActivity"));
			finish();
		} else {
			Toast.makeText(getBaseContext(), "Sem acesso à internet. Favor verificar.",
					Toast.LENGTH_LONG).show();
			finish();
		}
		
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