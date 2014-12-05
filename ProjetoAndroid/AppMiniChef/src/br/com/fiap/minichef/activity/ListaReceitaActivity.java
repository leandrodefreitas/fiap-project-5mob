package br.com.fiap.minichef.activity;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

public class ListaReceitaActivity extends Activity {
	
    private SearchView mSearchView;
    private ListView mListView;
    private Filter f;  	
    private TextView tvSearch;
    private List<ReceitaVO> receitas;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_receita_activity);
		
		Bundle extras = getIntent().getExtras();

		String origem = extras.getString("tipo");
		
		tvSearch = (TextView) findViewById(R.id.textViewSearch);
		
		
		if (origem.equals("ingrediente")){
			
			IngredienteVO ingrediente = (IngredienteVO) extras.getSerializable("vo");
			
			tvSearch.setText("Receitas com " + ingrediente.getDescricao());			
			
			ReceitaSCN recSCN = new ReceitaSCN(getApplicationContext());
			receitas = recSCN.obterReceitasPorIngrediente(ingrediente);
	        
		} else {
			
			CategoriaVO categoria = (CategoriaVO) extras.getSerializable("vo");

			tvSearch.setText(origem);
			
			ReceitaSCN recSCN = new ReceitaSCN(getApplicationContext());
			receitas = recSCN.obterReceitasPorCategoria(categoria);
		}
		
		mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);	
        
        ArrayAdapter<ReceitaVO> recAdapter = new ArrayAdapter<ReceitaVO>(getApplicationContext(), 
        		R.layout.search_list_item, 
        		receitas);
        mListView.setAdapter(recAdapter);
        
        f = recAdapter.getFilter();
        mListView.setTextFilterEnabled(true);
        setupSearchView();
        
        mListView.setOnItemClickListener(new OnItemClickListener() {
        	@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				ReceitaVO recVO = (ReceitaVO) parent.getItemAtPosition(position);
				
				Intent i = new Intent(getApplicationContext() ,DetalheReceitaActivity.class);
				i.putExtra("vo", recVO);
                startActivity(i);
			}
        });		
		
		
		Button voltarButton = (Button) findViewById(R.id.voltarSearch);
		voltarButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View view) {
				finish();
			}
		});		
		 
	}
	
    private void setupSearchView() {
        mSearchView.setIconifiedByDefault(false);
        mSearchView.setOnQueryTextListener(new SearchFiltro());
        mSearchView.setQueryHint("O que procura?");

		int id = mSearchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) mSearchView.findViewById(id);
		textView.setTextColor(Color.parseColor("#CC0001"));
		textView.setHintTextColor(Color.BLACK);
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
