package br.com.fiap.minichef.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.SearchView;
import android.widget.SearchView.OnQueryTextListener;
import br.com.fiap.minichef.adapter.MeuFragmentPagerAdapter;

public class MenuActivity extends FragmentActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        FragmentManager fm = getSupportFragmentManager();

        // Instancioa o FragmentPagerAdapter
        MeuFragmentPagerAdapter adapter = new MeuFragmentPagerAdapter(fm);

        // Seta o adapter do ViewPager
        viewPager.setAdapter(adapter);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        /*getMenuInflater().inflate(R.menu.menu, menu);
        

		SearchView sv = (SearchView) menu.findItem(R.id.item1).getActionView();
		sv.setOnQueryTextListener(new SearchFiltro());
		*/
        return true;
    }

    public void onClickMassa(View view) {
        Log.i("Click", "Clicou na opção massa");

        Intent intentPrato = new Intent(this,EscolherPratoActivity.class);
        intentPrato.putExtra("tipoPrato", "Massas");
        startActivity(intentPrato);
    }
    
	private class SearchFiltro implements OnQueryTextListener{
		
		

		@Override
		public boolean onQueryTextSubmit(String query) {
			Log.i("SearchView", "onQueryTextSubmit -> "+query);
			return false;
		}

		@Override
		public boolean onQueryTextChange(String newText) {
			Log.i("SearchView", "onQueryTextChange -> "+newText);
			return false;
		}
		
	}
}