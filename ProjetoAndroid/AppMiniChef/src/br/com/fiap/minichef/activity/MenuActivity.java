package br.com.fiap.minichef.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;
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

    public void onClickMassas(View view) {
        Log.i("Click", "Categoria massas");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Massas");
        startActivity(intentPrato);
    }
    public void onClickLanches(View view) {
        Log.i("Click", "Categoria lanches");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Lanches");
        startActivity(intentPrato);
    }
    public void onClickCarnes(View view) {
        Log.i("Click", "Categoria carnes");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Carnes");
        startActivity(intentPrato);
    }
    public void onClickSaladas(View view) {
        Log.i("Click", "Categoria saladas");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Saladas");
        startActivity(intentPrato);
    }
    public void onClickSopas(View view) {
        Log.i("Click", "Categoria sopas");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Sopas");
        startActivity(intentPrato);
    }
    public void onClickPetiscos(View view) {
        Log.i("Click", "Categoria massas");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Massas");
        startActivity(intentPrato);
    }
    public void onClickSobremesas(View view) {
        Log.i("Click", "Categoria sobremesas");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Sobremesas");
        startActivity(intentPrato);
    }
    public void onClickCompletos(View view) {
        Log.i("Click", "Categoria completos");
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipoPrato", "Completos");
        startActivity(intentPrato);
    }
    
	private class SearchFiltro implements OnQueryTextListener {
		
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
	
	public void onBackPressed() { 
		new AlertDialog.Builder(this)
    	.setTitle("Sair")
    	.setMessage("Deseja realmente sair do Mini Chef?")
    	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
    		@Override
    		public void onClick(DialogInterface dialog, int which) {
    			finish();
    			Toast.makeText(getBaseContext(), "Obrigado por utilizar este app.",
    					Toast.LENGTH_LONG).show();
    		}
    	})
    	.setNegativeButton(getString(R.string.nao), null)
    	.show();
	}
	
}