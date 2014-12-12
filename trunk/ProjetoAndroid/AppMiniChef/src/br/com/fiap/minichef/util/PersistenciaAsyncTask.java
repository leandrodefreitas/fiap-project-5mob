package br.com.fiap.minichef.util;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.ParseException;
import com.parse.ParseFile;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.CategoriaSCN;
import br.com.fiap.minichef.services.scn.ExcluirDadosSCN;
import br.com.fiap.minichef.services.scn.IngredienteSCN;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

public class PersistenciaAsyncTask extends AsyncTask<Void, Void, Void> {

	private ProgressDialog dialog;
	private Context context;
	private List<ParseObject> receitas;
	
	public PersistenciaAsyncTask(Context context) {
		this.context = context;
		
		if (!isConnected()){
			Toast.makeText(((Activity) context).getBaseContext(), "Sem acesso à internet. Favor verificar.",
					Toast.LENGTH_LONG).show();
			this.cancel(true);
			context.startActivity(new Intent(".MenuActivity"));
			((Activity) context).finish();
		}
	}
	
	public PersistenciaAsyncTask(Context context, Boolean atualizacao) {
		this.context = context;
		
		if (!isConnected()){
			Toast.makeText(((Activity) context).getBaseContext(), "Sem acesso à internet. Favor verificar.",
					Toast.LENGTH_LONG).show();
			this.cancel(true);
			context.startActivity(new Intent(".MenuActivity"));
			((Activity) context).finish();
		} else {
			if (atualizacao) {
				ExcluirDadosSCN base = new ExcluirDadosSCN(context);
				base.excluiDadosItemIngredientes();
    			base.excluiDadosIngredientes();
    			base.excluiDadosReceitas();
    			base.excluiDadosReceitaCategorias();
    			base.excluiDadosCategorias();
			}
		}
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
		query.orderByDescending("_updated_at");
		query.whereNotEqualTo("aprovado", false);
		try {
			receitas = query.find();
			persistenciaDados();
		} catch (ParseException e) {
			Log.d("Falha chamada no parse.", e.getLocalizedMessage());
			Log.e("parseReceita", "Erro ao buscar o resultado no parse");
			Toast.makeText(context, "Houve um erro ao buscar as receitas", Toast.LENGTH_SHORT).show();	
		}
        return null;
	}
	
	@Override
	protected void onPostExecute(Void result) {
		dialog.dismiss();
		context.startActivity(new Intent(".MenuActivity"));
		((Activity) context).finish();
	}
	
	
	// metodo para verificar conexao com a internet
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;           	
        } else {
            return false;              	
        }
    }
    
    // metodo que realiza a persistencia de dados
    public void persistenciaDados() {
    	CategoriaVO categoria;
		IngredienteVO ingrediente;
		ReceitaVO receita;
		
		ReceitaSCN receitaSCN = new ReceitaSCN(context);
		CategoriaSCN categoriaSCN = new CategoriaSCN(context);
		IngredienteSCN ingredienteSCN = new IngredienteSCN(context);
		
		if (receitas != null) {
            for (ParseObject receitaParse : receitas) {
            	
            	if (!receitaSCN.verSeTemReceita((String) receitaParse.get("nome"))) {
            		receita = new ReceitaVO();
                	
                	receita.setNome((String) receitaParse.get("nome"));
                	receita.setDescricao((String) receitaParse.get("descricao"));
                	receita.setData((Date) receitaParse.get("updatedAt"));
                	receita.setData(new Date()); // Corrigir depois
                	receita.setTempo((Integer) receitaParse.get("tempo"));
                	receita.setNota((Integer) receitaParse.get("nota"));
                	ParseFile fotoFile = (ParseFile) receitaParse.get("foto");
                	if (fotoFile != null) {
                		receita.setFoto(fotoFile.getUrl());
                	} else {
                		receita.setFoto("sem imagem");
                	}
                	
                	ArrayList<String> categoriasParse = new ArrayList<String>();
                	categoriasParse = (ArrayList<String>) receitaParse.get("categorias");
                	
        			List<CategoriaVO> listaCat = new ArrayList<CategoriaVO>();         
        			
                	for (String catParse : categoriasParse) {
            			categoria = new CategoriaVO();

            			if (!categoriaSCN.verSeTemCategoria(catParse)) {
            				categoria.setDescricao(catParse);
                			Long idcat = categoriaSCN.salvarCategoria(categoria);
                			categoria.setId(idcat.intValue());
                			listaCat.add(categoria);
                		} else {
                			categoria = categoriaSCN.obterCategoriaPorDescricao(catParse);
                			listaCat.add(categoria);
                		}
            		}
            		receita.setCategorias(listaCat);
                	
                	receita.setCategoria((categoriasParse).toString());

                	ArrayList<String> ingredientesParse = new ArrayList<String>();
                	ingredientesParse = (ArrayList<String>) receitaParse.get("ingredientes");
                	
            		List<IngredienteVO> listaIng = new ArrayList<IngredienteVO>();    
            		
                	for (String ingParse : ingredientesParse) {
                		ingrediente = new IngredienteVO();
                		String IngSep[] = ingParse.split(";");

                		if (!ingredienteSCN.verSetemIngrediente(IngSep[0])) {
                			ingrediente.setDescricao(IngSep[0]);
                			Long iding = ingredienteSCN.salvarIngrediente(ingrediente);
                			ingrediente.setId(iding.intValue());
                			ingrediente.setQuantidade(Double.parseDouble(IngSep[1]));
                			ingrediente.setUnidadeMedida(IngSep[2]);
                			listaIng.add(ingrediente);
                		} else {
                			ingrediente = ingredienteSCN.obterIngredientePorNome(IngSep[0]);
                			ingrediente.setQuantidade(Double.parseDouble(IngSep[1]));
                			ingrediente.setUnidadeMedida(IngSep[2]);
                			listaIng.add(ingrediente);
                		}
                	}
            		receita.setIngredientes(listaIng);
                	
                	receitaSCN.salvarReceita(receita);
            	}
            }
        }
    }

}
