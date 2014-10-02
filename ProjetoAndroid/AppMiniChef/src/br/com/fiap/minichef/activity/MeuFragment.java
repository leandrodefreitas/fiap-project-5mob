package br.com.fiap.minichef.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MeuFragment extends Fragment {
	
	private int mPaginaAtual;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
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

        switch (mPaginaAtual)
        {
            case 1:
                view = inflater.inflate(R.layout.categoria_layout, container, false);
                break ;
            case 2:
                view = inflater.inflate(R.layout.escolher_prato, container, false);
                break ;
            default:
                break ;
        }

        return view;
	}

}
