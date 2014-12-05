package br.com.fiap.minichef.activity;

import br.com.fiap.minichef.common.vo.ReceitaVO;
import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalheReceitaActivity extends Activity {

	private TextView tvnome;
	private TextView tvdescricao;
	private TextView tvdata;
	private TextView tvtempo;
	private TextView tvnota;
	private TextView tvfoto;
	private TextView tvcategoria;
	private TextView tvingrediente;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ReceitaVO receitavo = ((ReceitaVO)getIntent().getSerializableExtra("vo"));

		setContentView(R.layout.detalhe_receita);
		setTitle("Blabla");

		tvnome = (TextView) findViewById(R.id.tvNome);
		tvdescricao = (TextView) findViewById(R.id.tvDescricao);
		tvdata = (TextView) findViewById(R.id.tvData);
		tvtempo = (TextView) findViewById(R.id.tvTempo);
		tvnota = (TextView) findViewById(R.id.tvNota);
		tvfoto = (TextView) findViewById(R.id.tvFoto);
		tvcategoria = (TextView) findViewById(R.id.tvCategoria);
		tvingrediente = (TextView) findViewById(R.id.tvIngrediente);

		if (receitavo != null) {
			tvnome.setText(receitavo.getNome());
			tvdescricao.setText(receitavo.getDescricao());
			tvdata.setText(receitavo.getDataFormatted());
			tvtempo.setText(receitavo.getTempo().toString());
			tvnota.setText(receitavo.getNota().toString());
			tvfoto.setText(receitavo.getFoto());
			tvcategoria.setText(receitavo.getCategoria());
			tvingrediente.setText(receitavo.getIngredientesString());
		}

		Button voltarButton = (Button) findViewById(R.id.voltar);
		voltarButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});
	}
	
//	public static void textSpeech(String descricao){
	
//		String[] descArray = descricao.split(".");
//		TextToSpeech tts = new TextToSpeech(this, this);
//		tts.setLanguage(new Locale("pt_BR"));
//		for (int i = 0; i < descArray.length; i++) {
//			tts.speak(descArray[i], TextToSpeech.QUEUE_ADD, null);
//			Thread.sleep(1000);
//		}

//	}

}
