package br.com.fiap.minichef.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Date;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ItemIngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaCategoriaVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.parse.*;

public class PersistenciaAsyncTask extends AsyncTask<String, Void, String> {

	private ProgressDialog dialog;
	private Context context;
	
	public PersistenciaAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context, "Minichef",
				"Carregando base de dados, por favor aguarde...", false,
				true);
	}

	@Override
	protected String doInBackground(String... urls) {
        return GET(urls[0]);
	}
	
	@Override
	protected void onPostExecute(String result) {
		Log.i("Result", result);
		dialog.dismiss();
		
	}

	private String GET(String url) {
        InputStream inputStream = null;
        String result = "";
        try {
 
            // create HttpClient
            HttpClient httpclient = new DefaultHttpClient();
 
            // make GET request to the given URL
            HttpResponse httpResponse = httpclient.execute(new HttpPost(url));
 
            // receive response as inputStream
            inputStream = httpResponse.getEntity().getContent();
 
            // convert inputstream to string
            if(inputStream != null){
                result = convertInputStreamToString(inputStream);
            	parseReceitaJSON(result);          	
            } else {
                result = "Não houve retorno";         	
            }
        } catch (Exception e) {
            Log.d("InputStream", e.getLocalizedMessage());
        }
 
        return result;
	}


	private String convertInputStreamToString(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader( new InputStreamReader(inputStream));
        String line = "";
        String result = "";
        while((line = bufferedReader.readLine()) != null)
            result += line;
 
        inputStream.close();
        return result;
	}
	
	private void parseReceitaJSON(String result){
		
		String jsonResultado = result;
		
		// tirando parenteses do resultado para pegar objeto JSON
		jsonResultado = jsonResultado.substring(1, jsonResultado.length() - 1);

		Log.i("jsonResultado", jsonResultado);
		
		JSONArray userArray;
		JSONObject userObject;
		
		CategoriaVO categoria = new CategoriaVO();
		IngredienteVO ingrediente = new IngredienteVO();
		ReceitaVO receita = new ReceitaVO();
		ItemIngredienteVO itemIngrediente = new ItemIngredienteVO();
		ReceitaCategoriaVO receitaCategoria = new ReceitaCategoriaVO();

		try	{
			userArray = new JSONArray(jsonResultado);
			
			for (int i = 0; i < userArray.length(); i++) {
				
				userObject = userArray.getJSONObject(i);
				
				String nomeR = userObject.getString("nome");
				String descricaoR = userObject.getString("descricao");
				String dataR = userObject.getString("updatedAt");
				Integer tempo = userObject.getInt("tempo");
				Integer nota = userObject.getInt("tempo");
				String foto = userObject.getString("foto");
			}
			

			/*// numero
			int numConcurso = userObject.getInt("Concurso");
			concurso.setNumConcurso(numConcurso);
			Log.i( "RYF", "numero: " + userObject.getInt( "Concurso" )) ;
			
			// data
			String strMilisegundos = userObject.getString("Data").replaceAll("\\D", "");
			Date data = new Date(Long.valueOf( strMilisegundos ));
			concurso.setData(data);
			Log.i( "RYF", "data: " + new Date( Long.valueOf( strMilisegundos ) ) ) ;
			
			// dezenas
			String strDezenas = userObject.getString( "Dezenas" ) ;
			String[] arrDezenas = strDezenas.split("\\|");
			ArrayList<Integer> dezenas = new ArrayList<Integer>();
			for (int i = 0; i < arrDezenas.length; i++) {
				dezenas.add(Integer.parseInt(arrDezenas[i])); 
			}
			concurso.setDezenas(dezenas);
			Log.i( "jsonResultado", strDezenas ) ;*/


		} catch (JSONException e){
			Log.e("parseReceitaJSON", "Erro ao buscar o resultado JSON");
			Toast.makeText(context, "Houve um erro ao buscar o resultado", Toast.LENGTH_SHORT).show();				
		}
	}

}
