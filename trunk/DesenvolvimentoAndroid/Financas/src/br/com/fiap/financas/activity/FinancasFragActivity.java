package br.com.fiap.financas.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.view.View;
import br.com.fiap.R;
import br.com.fiap.financas.util.Constantes;

import com.actionbarsherlock.app.SherlockFragmentActivity;

public class FinancasFragActivity extends SherlockFragmentActivity {

	// Declare Variables
	private FragmentTabHost mTabHost;
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		setContentView(R.layout.main_fragment);
		
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		
		mTabHost.setup(this, getSupportFragmentManager(), R.id.tabcontent);

		mTabHost.addTab(mTabHost.newTabSpec(Constantes.TABS[0].toString()).setIndicator(Constantes.TABS[0].toString()), 
				GanhosFragment.class, null);
		
		mTabHost.addTab(mTabHost.newTabSpec(Constantes.TABS[1].toString()).setIndicator(Constantes.TABS[1].toString()),
				GastosFragment.class, null);
		
		mTabHost.addTab(mTabHost.newTabSpec(Constantes.TABS[2].toString()).setIndicator(Constantes.TABS[2].toString()),
				TotalFragment.class, null);
		
	}
    
    public void voltarDashboard(View v) {
		finish();
	}
    
}