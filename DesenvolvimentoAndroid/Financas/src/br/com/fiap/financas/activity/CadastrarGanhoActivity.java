package br.com.fiap.financas.activity;

import br.com.fiap.R;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

public class CadastrarGanhoActivity extends Activity{
	
	TextView dataGanho;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.cadastro_ganho);
		
		Bundle param = getIntent().getExtras();
		String data = param.getString ("data");
		dataGanho = (TextView) findViewById(R.id.lblDataGanho);
		
		dataGanho.setText(data);
		
		
		
	}
}
