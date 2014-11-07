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
import br.com.fiap.financas.adapter.FinancasGastosAdapter;
import br.com.fiap.financas.common.vo.GastoVO;
import br.com.fiap.financas.services.scn.GastoSCN;

import com.actionbarsherlock.app.SherlockFragment;

public class GastosFragment extends SherlockFragment {

	private List<GastoVO> gastoList = new ArrayList<GastoVO>();
	Context context;
	View rootView;
	ListView list;
	FinancasGastosAdapter adapter;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.financas_gastos, container, false);
		context =  getActivity(); 

		GastoSCN gastoSCN =  new GastoSCN(context);
		gastoList = gastoSCN.obterTodosGastos();
		list = (ListView) rootView.findViewById(R.id.listview);
		adapter = new FinancasGastosAdapter(getActivity(), gastoList);
		list.setAdapter(adapter);

		return rootView;
	}
}
