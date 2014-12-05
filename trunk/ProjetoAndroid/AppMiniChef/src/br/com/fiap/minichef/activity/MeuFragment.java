package br.com.fiap.minichef.activity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.IngredienteSCN;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

public class MeuFragment extends Fragment {
	
	private int mPaginaAtual;
	private Context context;
	
    private SearchView mSearchView;
    private ListView mListView;
    private Filter f;  
    private ArrayAdapter<String> adapter;
    
    private IngredienteVO ingrediente = new IngredienteVO();
	
	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		context=activity;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// Pega os argumentos do objeto Bundle
        Bundle data = getArguments();
 
        // Pega o valor a pagina atual passada por parametro via Bubdle 
        mPaginaAtual = data.getInt("pagina_atual", 0);
        
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Infla o layout do fragment
		final LayoutInflater inflaterAux = inflater;
		final ViewGroup containerAux = container;
        View view = null;

        switch (mPaginaAtual)
        {
            case 1:
            	
                view = inflater.inflate(R.layout.categoria_layout, container, false);
                break ;
                
            case 2:
                
                view = inflater.inflate(R.layout.lista_receitas, container, false);
                
                ReceitaSCN recSCN = new ReceitaSCN(context);
                List<ReceitaVO> receitas = recSCN.obterTodosReceitas();

                mSearchView = (SearchView) view.findViewById(R.id.search_view);
                mListView = (ListView) view.findViewById(R.id.list_view);
                
                ArrayAdapter<ReceitaVO> adapterReceitas = new ArrayAdapter<ReceitaVO>(context,
                        R.layout.search_list_item,
                        receitas);
                
                mListView.setAdapter(adapterReceitas);
                
                f = adapterReceitas.getFilter();
                
                mListView.setTextFilterEnabled(true);
                
                mListView.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						ReceitaVO recVO = (ReceitaVO) parent.getItemAtPosition(position);
						
						Intent i = new Intent(context,DetalheReceitaActivity.class);
						//i.putExtra(name, value)
						//i.putExtra(recVO);
                        startActivity(i);
					}
                });
                
                
                setupSearchView();  
                
                
                break ;
                
            case 3:
                view = inflater.inflate(R.layout.lista_ingredientes, container, false);  
                
                ArrayList<String> listaIngredientes = new ArrayList<String>();
                IngredienteSCN ingSCN = new IngredienteSCN(context);
                List<IngredienteVO> listaIng = ingSCN.obterTodasIngredientes();
                
                if (listaIng.isEmpty()){
                	Log.i("Erro","lista de ingredientes vazia");
                } else {
                	for (IngredienteVO ingVO : listaIng) {
                		listaIngredientes.add(ingVO.getDescricao());
                    }
                }
                Collections.sort(listaIngredientes);

                mSearchView = (SearchView) view.findViewById(R.id.search_view);
                mListView = (ListView) view.findViewById(R.id.list_view);
                
                /*adapter = new ArrayAdapter<String>(context,
                        R.layout.search_list_item,
                        listaIngredientes);*/
                
                ArrayAdapter<IngredienteVO> ingAdapter = new ArrayAdapter<IngredienteVO>(context,
                        R.layout.search_list_item,
                        listaIng);
                
                mListView.setAdapter(ingAdapter);
                
                f = ingAdapter.getFilter();
                
                mListView.setTextFilterEnabled(true);
                
                setupSearchView(); 

                mListView.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						
						ingrediente = (IngredienteVO) parent.getItemAtPosition(position);
						
				        Intent intent = new Intent(context, ListaReceitaActivity.class);
				        intent.putExtra("tipo", "ingrediente");
				        intent.putExtra("vo", ingrediente);
						startActivity(intent);
						
						
						
					}
				});
                
                break ;
            	
            default:
                break ;
        }

        return view;
	}
	
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchFiltro());
        mSearchView.setQueryHint("O que procura?");

		int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) mSearchView.findViewById(id);
		textView.setTextColor(Color.parseColor("#CC0001"));
		textView.setHintTextColor(Color.WHITE);
    }
    
	private class SearchFiltro implements OnQueryTextListener{
		@Override
		public boolean onQueryTextSubmit(String query) {
			Log.i("SearchView", "onQueryTextSubmit -> "+query);
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
	        if (TextUtils.isEmpty(newText)) {
	            //mListView.clearTextFilter();
	            f.filter(null);
	        } else {
	            //mListView.setFilterText(newText.toString());
	            f.filter(newText);
	        }
	        return true;
		}
	}
	

}
