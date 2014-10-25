package br.com.fiap.financas.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.fiap.R;

public class GastosFragment extends Fragment {
	
	Context thiscontext;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.financas_gastos, container, false);
		thiscontext = container.getContext();
		
		return rootView;
	}
}
