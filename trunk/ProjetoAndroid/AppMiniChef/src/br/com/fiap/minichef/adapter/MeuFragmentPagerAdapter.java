package br.com.fiap.minichef.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import br.com.fiap.minichef.activity.MeuFragment;

public class MeuFragmentPagerAdapter extends FragmentPagerAdapter {
	
	final int TOTAL_PAGINAS = 3;

	public MeuFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		MeuFragment meuFragment = new MeuFragment();
		
		//Passa a posicao do item como parametro para o fragment
		Bundle data = new Bundle();
		data.putInt("pagina_atual", position + 1);		
		meuFragment.setArguments(data);
		
		return meuFragment;
	}

	@Override
	public int getCount() {
		return TOTAL_PAGINAS;
	}
	
	@Override
    public CharSequence getPageTitle(int position) {

        String msg = "" ;

        switch (position)
        {
            case 0:
                msg =  "Categorias";
                break ;
            case 1:
                msg =  "Receitas";
                break ;
            case 2:
                msg =  "Ingredientes";
                break ;                
            default:
                break ;
        }

        return msg;
    }

}
