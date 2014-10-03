package br.com.fiap.minichef.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;

public class MeuFragment extends Fragment {
	
	private int mPaginaAtual;
	private List<String> listGroup;
	private HashMap<String, List<String>> listData;
	private Context context;
	
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
            default:
                break ;
        }

        return view;
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

}
