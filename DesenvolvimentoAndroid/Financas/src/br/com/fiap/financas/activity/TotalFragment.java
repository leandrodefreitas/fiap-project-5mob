package br.com.fiap.financas.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.com.fiap.R;

import com.actionbarsherlock.app.SherlockFragment;
 
public class TotalFragment extends SherlockFragment {
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.financas_totais, container, false);
         
        return rootView;
    }
 
}