package br.com.fiap.minichef.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class CategoriaActivity extends Activity {
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.categoria_layout);
		
	}

    public void onClickMassa(View view) {
        Log.i("Click","Clicou na opção massa");

        Intent intentPrato = new Intent(this,EscolherPratoActivity.class);
        intentPrato.putExtra("tipoPrato", "Massas");
        startActivity(intentPrato);

    }

}
