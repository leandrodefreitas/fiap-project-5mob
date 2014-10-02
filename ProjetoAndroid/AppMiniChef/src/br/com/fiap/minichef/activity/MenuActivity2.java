package br.com.fiap.minichef.activity;

import br.com.fiap.minichef.activity.R;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;

public class MenuActivity2 extends Activity implements OnClickListener{
	
	ImageView imgMassas;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//setContentView(R.layout.outro_menu);
		
		//imgMassas = (ImageView) findViewById(R.id.imageView1);
		
		//imgMassas.setOnClickListener(this);
		
		
	}

	@Override
	public void onClick(View v) {
		
		if (v == imgMassas) {
			
			Intent intentPrato = new Intent(this,EscolherPratoActivity.class);
			intentPrato.putExtra("tipoPrato", "Massas");
			startActivity(intentPrato);			
		}
		
	}
}
