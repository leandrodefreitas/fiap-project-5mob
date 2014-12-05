package br.com.fiap.minichef.activity;

import java.io.InputStream;
import java.util.List;
import java.util.Locale;

import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.ReceitaSCN;
import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DetalheReceitaActivity extends Activity {

	private TextView tvdescricao;
	private TextView tvtempo;
	private TextView tvnota;
	private ImageView ivfoto;
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
		
		ivfoto = (ImageView) findViewById(R.id.ivFoto);
		tvdescricao = (TextView) findViewById(R.id.tvDescricao);
		tvtempo = (TextView) findViewById(R.id.tvTempo);
		tvnota = (TextView) findViewById(R.id.tvNota);
		tvcategoria = (TextView) findViewById(R.id.tvCategoria);
		tvingrediente = (TextView) findViewById(R.id.tvIngrediente);

		if (receitavo != null) {
			if (receitavo.getFoto().contains("http") && isConnected()) {
				new DownloadImageTask(ivfoto).execute(receitavo.getFoto());
			} else {
				ivfoto.setImageResource(R.drawable.sraminichef);
			}
			
			String categoriasS = receitavo.getCategoria();
			categoriasS = categoriasS.substring(1, categoriasS.length() - 1);
			tvcategoria.setText("Categoria: "+categoriasS+".");
			
			Integer tempoPrep = receitavo.getTempo();
			tempoPrep = tempoPrep/60;
			tvtempo.setText("Tempo de preparo: " + tempoPrep + "min.");
			
			String estrelas = "★ ★ ★ ★ ★";
			switch (receitavo.getNota()) {
	        	case 1:
	        		estrelas = "★ ☆ ☆ ☆ ☆";
	        		break;
	        	case 2:
	        		estrelas = "★ ★ ☆ ☆ ☆";
	        		break;
	        	case 3:
	        		estrelas = "★ ★ ★ ☆ ☆";
	        		break;
	        	case 4:
	        		estrelas = "★ ★ ★ ★ ☆";
	        		break;
	        	case 5:
	        		estrelas = "★ ★ ★ ★ ★";
	        		break;
	        	default: estrelas = "☆ ☆ ☆ ☆ ☆";
	        }
			tvnota.setText("Nota: " + estrelas +"\n");
			
			ReceitaSCN recscn = new ReceitaSCN(getApplicationContext());
			List<IngredienteVO> listaIng = recscn.obterIngredientesPorId(receitavo.getId());
			String ingredientesString = "";
			for (IngredienteVO ingVO : listaIng) {
				if(ingVO.getQuantidade().toString().contains(".0")) {
					ingredientesString += "   • " + ingVO.getQuantidade().intValue() + " " 
							+ ingVO.getUnidadeMedida() + " de " + ingVO.getDescricao() + "\n";
				} else {
					ingredientesString += "   • " + ingVO.getQuantidade().toString() + " " 
							+ ingVO.getUnidadeMedida() + " de " + ingVO.getDescricao() + "\n";
				}
			}
			tvingrediente.setText("Ingredientes: \n" + ingredientesString);
			
			tvdescricao.setText(receitavo.getDescricao() + "\n");
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
				tts.setLanguage(new Locale("pt_BR"));
				tts.speak(((String) tvdescricao.getText()), TextToSpeech.QUEUE_ADD, null);
			}
		});
	}
	
	private class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
	    ImageView bmImage;

	    public DownloadImageTask(ImageView bmImage) {
	        this.bmImage = bmImage;
	    }

	    protected Bitmap doInBackground(String... urls) {
	        String urldisplay = urls[0];
	        Bitmap mIcon11 = null;
	        try {
	            InputStream in = new java.net.URL(urldisplay).openStream();
	            mIcon11 = BitmapFactory.decodeStream(in);
	        } catch (Exception e) {
	            Log.e("Error", e.getMessage());
	            e.printStackTrace();
	        }
	        return mIcon11;
	    }

	    protected void onPostExecute(Bitmap result) {
	        bmImage.setImageBitmap(result);
	    }
	}
	
	// metodo para verificar conexao com a internet
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
