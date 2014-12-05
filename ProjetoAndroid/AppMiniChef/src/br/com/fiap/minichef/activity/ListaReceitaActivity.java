package br.com.fiap.minichef.activity;

import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;
import br.com.fiap.minichef.common.vo.IngredienteVO;
import br.com.fiap.minichef.common.vo.ReceitaVO;
import br.com.fiap.minichef.services.scn.ReceitaSCN;

public class ListaReceitaActivity extends Activity{
	
    private SearchView mSearchView;
    private ListView mListView;
    private Filter f;  	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.lista_receitas);
		
		IngredienteVO ingrediente = ((IngredienteVO)getIntent().getSerializableExtra("vo"));
		
		ReceitaSCN recSCN = new ReceitaSCN(getApplicationContext());
		List<ReceitaVO> receitas = recSCN.obterReceitasPorIngrediente(ingrediente);
        
		mSearchView = (SearchView) findViewById(R.id.search_view);
        mListView = (ListView) findViewById(R.id.list_view);	
        
        ArrayAdapter<ReceitaVO> recAdapter = new ArrayAdapter<ReceitaVO>(getApplicationContext(), 
        		R.layout.search_list_item, 
        		receitas);
        mListView.setAdapter(recAdapter);
        
        f = recAdapter.getFilter();
        mListView.setTextFilterEnabled(true);
        setupSearchView(); 		
		
		
		
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
