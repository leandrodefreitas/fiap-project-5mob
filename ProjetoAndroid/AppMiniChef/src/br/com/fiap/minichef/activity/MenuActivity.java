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
import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.services.scn.CategoriaSCN;

public class MenuActivity extends FragmentActivity {
	
	private CategoriaVO categoria = new CategoriaVO();
	
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
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnMassas));
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria);
        startActivity(intentPrato);
    }
    public void onClickLanches(View view) {
        Log.i("Click", "Categoria lanches");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnLanches));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria);       
        startActivity(intentPrato);
    }
    public void onClickCarnes(View view) {
        Log.i("Click", "Categoria carnes");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnCarnes));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
    }
    public void onClickSaladas(View view) {
        Log.i("Click", "Categoria saladas");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnSaladas));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
    }
    public void onClickSopas(View view) {
        Log.i("Click", "Categoria sopas");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnSopas));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
    }
    public void onClickPetiscos(View view) {
        Log.i("Click", "Categoria petiscos");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnPetiscos));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
    }
    public void onClickSobremesas(View view) {
        Log.i("Click", "Categoria sobremesas");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnSobremesas));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
    }
    public void onClickCompletos(View view) {
        Log.i("Click", "Categoria completos");
        
        String strCategoria = String.valueOf(view.getResources().getText(R.string.btnRefeicaoCompleta));  
        
        CategoriaSCN catSCN = new CategoriaSCN(getApplicationContext());
        categoria = catSCN.obterCategoriaPorDescricao(strCategoria);
        
        Intent intentPrato = new Intent(this,ListaReceitaActivity.class);
        intentPrato.putExtra("tipo", strCategoria);
        intentPrato.putExtra("vo", categoria); 
        startActivity(intentPrato);
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