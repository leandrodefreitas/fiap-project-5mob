package br.com.fiap.minichef.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class DetalheReceitaActivity extends Activity {

	private EditText nameText;
	private int position;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.detalhe_receita);
		setTitle("Blabla");

		nameText = (EditText) findViewById(R.id.name);

		Bundle extras = getIntent().getExtras();
		if (extras != null) {
			String name = extras.getString("descricao");
			position = extras.getInt("position");

			if (name != null) {
				nameText.setText(name);
			}
		}

		Button confirmButton = (Button) findViewById(R.id.confirm);
		confirmButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				Bundle bundle = new Bundle();
				bundle.putString("descricao", nameText.getText().toString());
				bundle.putInt("position", position);

				Intent intent = new Intent();
				intent.putExtras(bundle);
				setResult(RESULT_OK, intent);
				finish();
			}
		});
	}

}
