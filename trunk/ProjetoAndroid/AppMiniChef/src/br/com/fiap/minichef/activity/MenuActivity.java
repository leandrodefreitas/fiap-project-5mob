package br.com.fiap.minichef.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import br.com.fiap.minichef.adapter.NavigationDrawerFragment;
import br.com.fiap.minichef.adapter.MeuFragmentPagerAdapter;
import br.com.fiap.minichef.common.vo.CategoriaVO;
import br.com.fiap.minichef.services.scn.CategoriaSCN;
import br.com.fiap.minichef.util.PersistenciaAsyncTask;

public class MenuActivity extends FragmentActivity implements
NavigationDrawerFragment.NavigationDrawerCallbacks {
	
	/**
	 * Fragment managing the behaviors, interactions and presentation of the
	 * navigation drawer.
	 */
	private NavigationDrawerFragment mNavigationDrawerFragment;
	
	private CategoriaVO categoria = new CategoriaVO();
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager viewPager = (ViewPager) findViewById(R.id.pager);

        android.support.v4.app.FragmentManager fm = getSupportFragmentManager();

        // Instancioa o FragmentPagerAdapter
        MeuFragmentPagerAdapter adapter = new MeuFragmentPagerAdapter(fm);

        // Seta o adapter do ViewPager
        viewPager.setAdapter(adapter);
        
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager()
				.findFragmentById(R.id.navigation_drawer);

		// Set up the drawer.
		mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
				(DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	if (!mNavigationDrawerFragment.isDrawerOpen()) {
			// Only show items in the action bar relevant to this screen
			// if the drawer is not showing. Otherwise, let the drawer
			// decide what to show in the action bar.
			getMenuInflater().inflate(R.menu.main, menu);
			restoreActionBar();
			return true;
		}
		return super.onCreateOptionsMenu(menu);
    }
    
	@Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
        switch (item.getItemId())
        {
        case R.id.menu_info:
        	new AlertDialog.Builder(this)
        	.setTitle("Informações").setIcon(android.R.drawable.ic_menu_info_details)
        	.setMessage(getString(R.string.sobreInformacoes))
        	.setNegativeButton(getString(R.string.voltar), null)
        	.show();
            return true;
 
        case R.id.menu_sair:
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
            return true;
 
        default:
            return super.onOptionsItemSelected(item);
        }
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

	@Override
	public void onNavigationDrawerItemSelected(int position) {
		// update the main content by replacing fragments
		FragmentManager fragmentManager = getFragmentManager();
		fragmentManager
				.beginTransaction()
				.replace(R.id.container,
						PlaceholderFragment.newInstance(position + 1)).commit();
	}

	public void onSectionAttached(int number) {
		switch (number) {
		case 1:
			
			break;
		case 2:
			Intent intent = new Intent(this, CadastrarReceitaActivity.class);       
	        startActivity(intent);
			break;
		case 3:
			new AlertDialog.Builder(this)
        	.setTitle("Atualizar")
        	.setMessage("Deseja realmente atualizar a base de dados do Mini Chef?")
        	.setPositiveButton(getString(R.string.sim), new DialogInterface.OnClickListener() {
        		@Override
        		public void onClick(DialogInterface dialog, int which) {
        			if (!isConnected()){
        				Toast.makeText(getBaseContext(), "Sem acesso à internet. Favor verificar.",
        						Toast.LENGTH_LONG).show();
        			} else {
	        			PersistenciaAsyncTask task = new PersistenciaAsyncTask(getApplicationContext(), true);
	        			task.execute();
        			}
        			
        		}
        	})
        	.setNegativeButton(getString(R.string.nao), null)
        	.show();
			break;
		case 4:
			new AlertDialog.Builder(this)
        	.setTitle("Informações").setIcon(android.R.drawable.ic_menu_info_details)
        	.setMessage(getString(R.string.sobreInformacoes))
        	.setNegativeButton(getString(R.string.voltar), null)
        	.show();
			break;
		case 5:
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
			break;
		}
	}

	public void restoreActionBar() {
		ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
		actionBar.setDisplayShowTitleEnabled(true);
	}
	
	public static class PlaceholderFragment extends Fragment {
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber) {
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment() {
		}

		@Override
		public void onAttach(Activity activity) {
			super.onAttach(activity);
			((MenuActivity) activity).onSectionAttached(getArguments().getInt(
					ARG_SECTION_NUMBER));
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		return null;
	}
	
	// metodo para verificar conexao com a internet
    public boolean isConnected() {
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Activity.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        if (networkInfo != null && networkInfo.isConnected()) {
            return true;           	
        } else {
            return false;              	
        }
    }
	
}