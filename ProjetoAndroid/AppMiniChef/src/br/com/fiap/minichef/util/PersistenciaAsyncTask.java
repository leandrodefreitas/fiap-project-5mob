package br.com.fiap.minichef.util;

import java.util.List;

import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

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

public class PersistenciaAsyncTask extends AsyncTask<Void, Void, Void> {

	private ProgressDialog dialog;
	private Context context;
	private List<ParseObject> receitas;
	
	public PersistenciaAsyncTask(Context context) {
		this.context = context;
	}

	@Override
	protected void onPreExecute() {
		dialog = ProgressDialog.show(context, "Minichef",
				"Carregando base de dados, por favor aguarde...", false, true);
		super.onPreExecute();
	}
	
	@Override
	protected void onProgressUpdate(Void... values) {
		super.onProgressUpdate(values);
	}


	@Override
	protected Void doInBackground(Void... params) {
		ParseQuery<ParseObject> query = new ParseQuery<ParseObject>("RECEITAS");
		query.orderByDescending("_created_at");
		try {
			receitas = query.find();
			
		} catch (ParseException e) {
			Log.d("Falha chamada no parse.", e.getLocalizedMessage());
			Log.e("parseReceita", "Erro ao buscar o resultado no parse");
			Toast.makeText(context, "Houve um erro ao buscar as receitas", Toast.LENGTH_SHORT).show();	
		}
        return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		CategoriaVO categoria = new CategoriaVO();
		IngredienteVO ingrediente = new IngredienteVO();
		ReceitaVO receita = new ReceitaVO();
		ItemIngredienteVO itemIngrediente = new ItemIngredienteVO();
		ReceitaCategoriaVO receitaCategoria = new ReceitaCategoriaVO();
		
		if (receitas != null) {
            for (ParseObject receitaParse : receitas) {
            	
            	receita.setNome((String) receitaParse.get("nome"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	receita.setData((String) receitaParse.get("_updated_at"));
            	
                //adapter.add();
            }
        }
		
		
		
		
		dialog.dismiss();
	}

}
