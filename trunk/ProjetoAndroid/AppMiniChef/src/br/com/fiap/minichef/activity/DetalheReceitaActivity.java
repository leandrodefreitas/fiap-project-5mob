package br.com.fiap.minichef.activity;

import java.util.Locale;

import br.com.fiap.minichef.common.vo.ReceitaVO;
import android.app.Activity;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class DetalheReceitaActivity extends Activity {

	private TextView tvdescricao;
	private TextView tvdata;
	private TextView tvtempo;
	private TextView tvnota;
	private TextView tvfoto;
	private TextView tvcategoria;
	private TextView tvingrediente;
	
	TextToSpeech tts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		ReceitaVO receitavo = ((ReceitaVO)getIntent().getSerializableExtra("vo"));
		
		tts = new TextToSpeech(getApplicationContext(), 
			new TextToSpeech.OnInitListener() {
			      @Override
			      public void onInit(int status) {
			         if(status != TextToSpeech.ERROR) {
			             tts.setLanguage(Locale.UK);
			         }				
			      }
			});

		setContentView(R.layout.detalhe_receita);
		setTitle(receitavo.getNome());

		tvdescricao = (TextView) findViewById(R.id.tvDescricao);
		tvdata = (TextView) findViewById(R.id.tvData);
		tvtempo = (TextView) findViewById(R.id.tvTempo);
		tvnota = (TextView) findViewById(R.id.tvNota);
		tvfoto = (TextView) findViewById(R.id.tvFoto);
		tvcategoria = (TextView) findViewById(R.id.tvCategoria);
		tvingrediente = (TextView) findViewById(R.id.tvIngrediente);

		if (receitavo != null) {
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
				tts.shutdown();
				finish();
			}
		});
		
		Button lerButton = (Button) findViewById(R.id.ler);
		lerButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				
				//String[] descArray = ((String) tvdescricao.getText()).split(".");
				tts.setLanguage(new Locale("pt_BR"));
				/*for (int i = 0; i < descArray.length; i++) {
					tts.speak(descArray[i], TextToSpeech.QUEUE_ADD, null);
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}*/
				tts.speak(((String) tvdescricao.getText()), TextToSpeech.QUEUE_ADD, null);
			}
		});
	}

}
