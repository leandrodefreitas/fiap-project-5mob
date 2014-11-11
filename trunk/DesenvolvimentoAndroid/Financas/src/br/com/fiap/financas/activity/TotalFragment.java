package br.com.fiap.financas.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import br.com.fiap.R;

import com.actionbarsherlock.app.SherlockFragment;
 
public class TotalFragment extends SherlockFragment {
	
	WebView wvGrafico;
	String strURL;
 
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
 
        View rootView = inflater.inflate(R.layout.financas_totais, container, false);
         
        strURL="http://chart.apis.google.com/chart?chxt=x,y&cht=bvs&chd=t:60,70,85,65,40&chco=76A4FB&chls=2.0&chs=180x150&chxl=0:|Jan|Fev|Mar|Abr|Mai";
        
        wvGrafico = (WebView) rootView.findViewById(R.id.wvGrafico);
		wvGrafico.loadUrl(strURL);
        	
        return rootView;
    }
 
}