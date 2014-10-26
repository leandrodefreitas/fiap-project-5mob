package br.com.fiap.financas.activity;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import br.com.fiap.R;
import br.com.fiap.financas.adapter.FinancasGanhosListAdapter;
import br.com.fiap.financas.common.vo.RegistroVO;
import br.com.fiap.financas.services.scn.RegistroSCN;

public class GastosFragment extends Fragment {

	Context thiscontext;

	private List<RegistroVO> registroList = new ArrayList<RegistroVO>();
	private ListView listView;
	private FinancasGanhosListAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		thiscontext = container.getContext();
		
		View rootView = inflater.inflate(R.layout.financas_gastos, container, false);
		
		listView = (ListView) rootView.findViewById(R.id.list);

//		RegistroSCN registro = new RegistroSCN(thiscontext);
//		registroList = (ArrayList<RegistroVO>) registro
//				.obterRegistrosPorTipo(1);
//		
//		String descricao = registroList.get(0).getDescricao();
//		Log.i("GASTOS", "REGISTRO):----------------------- " + descricao);
//
//		adapter = new FinancasGanhosListAdapter(this, registroList);
//		listView.setAdapter(adapter);

		return listView;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
	}
}
