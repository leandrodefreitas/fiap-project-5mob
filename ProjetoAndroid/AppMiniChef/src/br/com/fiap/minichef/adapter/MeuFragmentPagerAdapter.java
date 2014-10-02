package br.com.fiap.minichef.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

import br.com.fiap.minichef.activity.MeuFragment;

public class MeuFragmentPagerAdapter extends FragmentPagerAdapter {
	
	// 10 p�ginas
	final int TOTAL_PAGINAS = 2;

	public MeuFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		MeuFragment meuFragment = new MeuFragment();
		
		// Passa a posi��o do item como 
		// par�metro para o fragment
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
                msg =  "Categoria";
                break ;
            case 1:
                msg =  "Ingrediente";
                break ;
            default:
                break ;
        }

        return msg;
    }

}
