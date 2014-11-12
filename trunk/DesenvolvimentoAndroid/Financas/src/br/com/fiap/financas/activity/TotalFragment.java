package br.com.fiap.financas.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.TextView;
import br.com.fiap.R;
import br.com.fiap.financas.services.scn.GanhoSCN;
import br.com.fiap.financas.services.scn.GastoSCN;
import br.com.fiap.financas.util.Util;

import com.actionbarsherlock.app.SherlockFragment;
 
public class TotalFragment extends SherlockFragment {
	
	WebView wvGrafico;
	String strURL;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.financas_totais, container, false);
        
		GanhoSCN ganhoA = new GanhoSCN(rootView.getContext());
		GastoSCN gastoA = new GastoSCN(rootView.getContext());
		Double ganhoTotal = ganhoA.obterTotalGanhos();
		Double gastoTotal = gastoA.obterTotalGastos();
		Double saldo = ganhoTotal - gastoTotal;
		
		strURL="http://chart.apis.google.com/chart?" +
        		"&chs=300x250" +
        		"&cht=bvs" +
        		"&chd=t:"+ gastoTotal + "," + saldo +
        		"&chxr=0,0,"+ ganhoTotal +
				"&chds=0," + ganhoTotal +
        		"&chxl=0:|Gastos|Saldo" + 
        		"&chco=FF2351|0AFF8A";
		
        
        wvGrafico = (WebView) rootView.findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
		
		TextView lblganho = (TextView) rootView.findViewById(R.id.lblTotalGanhos);
		lblganho.setText("Total de ganhos: " + Util.formataMoedaBRL(ganhoTotal));
		TextView lblgasto = (TextView) rootView.findViewById(R.id.lblTotalGastos);
		lblgasto.setText("Total de gastos: " + Util.formataMoedaBRL(gastoTotal));
		TextView lblsaldo = (TextView) rootView.findViewById(R.id.lblSaldo);
		lblsaldo.setText("Saldo: " + Util.formataMoedaBRL(saldo));
        	
        return rootView;
    }
 
}