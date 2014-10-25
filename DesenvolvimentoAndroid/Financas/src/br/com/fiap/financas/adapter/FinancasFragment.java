package br.com.fiap.financas.adapter;

import br.com.fiap.R;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FinancasFragment extends Fragment {
	
	private int mPaginaAtual;
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
 
        // Pega o valor a p�gina atual passada por par�metro via Bubdle 
        mPaginaAtual = data.getInt("pagina_atual", 0);
        
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Infla o layout do fragment
        System.out.println("mPaginaAtual: " + mPaginaAtual);

        View view = null;

        switch (mPaginaAtual) {
            case 1:
                view = inflater.inflate(R.layout.financas_ganhos, container, false);
                break;
            case 2:
                view = inflater.inflate(R.layout.financas_gasros, container, false);
                break;
            case 3:
                view = inflater.inflate(R.layout.financas_totais, container, false);
                break;
            default:
                break;
        }
        return view;
	}

}
