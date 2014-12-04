package br.com.fiap.minichef.activity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import android.widget.TextView;

public class MeuFragment extends Fragment {
	
	private int mPaginaAtual;
	private List<String> listGroup;
	private HashMap<String, List<String>> listData;
	private Context context;
	
    private SearchView searchView;
    private ListView listView;
    private Filter f;  
    private ArrayAdapter<String> adapter;
    
    public static final String[] receitas = {"Bolo de fubá", "Nega Maluca", "Omelete", "Salada de Cenoura", "Lasanha Quatro Queijos"
    };
    
    public static final String[] ingredientes = {"Ovo", "Fubá", "Cebola", "Chocolate", "Farinha", "Manteiga", "Abobrinha", "Teste", 
    	"Chocolate em Pó", "Casca de Ovo", "Queijo Parmesão"
    };    
	
	
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
 
        // Pega o valor a pï¿½gina atual passada por parï¿½metro via Bubdle 
        mPaginaAtual = data.getInt("pagina_atual", 0);
        

	      
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Infla o layout do fragment
        System.out.println("mPaginaAtual: " + mPaginaAtual);

        View view = null;

        switch (mPaginaAtual)
        {
            case 1:
            	
                view = inflater.inflate(R.layout.categoria_layout, container, false);
                break ;
                
            case 2:
            	/*
                view = inflater.inflate(R.layout.ingredient_list, container, false);
                
        		buildList();
        		
        		ExpandableListView expandableListView = (ExpandableListView) view.findViewById(R.id.expandableListView);
        		expandableListView.setAdapter(new br.com.fiap.minichef.adapter.ExpandableAdapter(context.getApplicationContext(), listGroup, listData));
        		
        		expandableListView.setChoiceMode(ExpandableListView.CHOICE_MODE_MULTIPLE);
        		
        		expandableListView.setOnChildClickListener(new OnChildClickListener(){
        			@Override
        			public boolean onChildClick(ExpandableListView parent, View v, int groupPosition, int childPosition, long id) {
        				//Toast.makeText(context, "Group: "+groupPosition+"| Item: "+childPosition, Toast.LENGTH_SHORT).show();
        				return false;
        			}
        		});
        		
        		expandableListView.setOnGroupExpandListener(new OnGroupExpandListener(){
        			@Override
        			public void onGroupExpand(int groupPosition) {
        				//Toast.makeText(context, "Group (Expand): "+groupPosition, Toast.LENGTH_SHORT).show();
        			}
        		});
        		
        		expandableListView.setOnGroupCollapseListener(new OnGroupCollapseListener(){
        			@Override
        			public void onGroupCollapse(int groupPosition) {
        				//Toast.makeText(context, "Group (Collapse): "+groupPosition, Toast.LENGTH_SHORT).show();
        			}
        		});
        		
        		expandableListView.setGroupIndicator(getResources().getDrawable(R.drawable.icon_group));               
                break ;
                */
                
                view = inflater.inflate(R.layout.lista_receitas, container, false);  
                
                List<String> listaReceitas = Arrays.asList(receitas);
                
                Collections.sort(listaReceitas);

                searchView = (SearchView) view.findViewById(R.id.search_view);
                listView = (ListView) view.findViewById(R.id.list_view);
                
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,
                        R.layout.search_list_item,
                        listaReceitas);
                
                listView.setAdapter(adapter);
                
                f = adapter.getFilter();
                
                listView.setTextFilterEnabled(true);
                
                setupSearchView();  
                break ;
                
            case 3:
                view = inflater.inflate(R.layout.lista_ingredientes, container, false);  
                
                List<String> listaIngredientes = Arrays.asList(ingredientes);
                
                Collections.sort(listaIngredientes);

                searchView = (SearchView) view.findViewById(R.id.search_view);
                listView = (ListView) view.findViewById(R.id.list_view);
                
                adapter = new ArrayAdapter<String>(context,
                        R.layout.search_list_item,
                        listaIngredientes);
                
                listView.setAdapter(adapter);
                
                f = adapter.getFilter();
                
                listView.setTextFilterEnabled(true);
                
                setupSearchView(); 
                break ;
            	
            default:
                break ;
        }

        return view;
	}
	
    private void setupSearchView() {
    	
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(new SearchFiltro());
        searchView.setQueryHint("O que procura?");

		int id = searchView.getContext().getResources().getIdentifier("android:id/search_src_text", null, null);
		TextView textView = (TextView) searchView.findViewById(id);
		textView.setTextColor(Color.parseColor("#CC0001"));
		textView.setHintTextColor(Color.WHITE);

    }	
	
	public void buildList(){
		listGroup = new ArrayList<String>();
		listData = new HashMap<String, List<String>>();
		
		// GROUP
			listGroup.add("A");
			listGroup.add("B");
			listGroup.add("C");
			listGroup.add("D");
			
		// CHILDREN
			List<String> auxList = new ArrayList<String>();
			auxList.add("Arroz");
			auxList.add("Atum");
			auxList.add("Acai’");
			auxList.add("Azeitona");
			listData.put(listGroup.get(0), auxList);
			
			auxList = new ArrayList<String>();
			auxList.add("Batata");
			auxList.add("Baunilha");
			auxList.add("Bife");
			auxList.add("Bombom");
			listData.put(listGroup.get(1), auxList);
			
			auxList = new ArrayList<String>();
			auxList.add("Cebola");
			auxList.add("Champignon");
			auxList.add("Chourico");
			auxList.add("Costela de Porco");
			listData.put(listGroup.get(2), auxList);
			
			auxList = new ArrayList<String>();
			auxList.add("Damasco");
			auxList.add("Doce de Leite");
			auxList.add("Danone");
			auxList.add("Doritos");
			listData.put(listGroup.get(3), auxList);
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
