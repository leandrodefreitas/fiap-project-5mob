package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.FinancasGanhosAdapter;
import br.com.fiap.financas.common.vo.GanhoVO;
import br.com.fiap.financas.services.scn.GanhoSCN;

import com.actionbarsherlock.app.SherlockFragment;

public class GanhosFragment extends SherlockFragment{
	
	private List<GanhoVO> ganhoList = new ArrayList<GanhoVO>();
	Context context;
	View rootView;
	ListView list;
	FinancasGanhosAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.financas_ganhos, container, false);
		
		context =  getActivity(); 

		GanhoSCN ganhoSCN =  new GanhoSCN(context);
		ganhoList = ganhoSCN.obterTodosGanhos();
		list = (ListView) rootView.findViewById(R.id.listview);
		adapter = new FinancasGanhosAdapter(getActivity(), ganhoList);
		list.setAdapter(adapter);

		return rootView;
	}
}
