package br.com.fiap.financas.adapter;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.Switch;

import br.com.fiap.financas.adapter.FinancasFragment;

public class FinancasFragmentPagerAdapter extends FragmentPagerAdapter {
	
	final int TOTAL_PAGINAS = 3;

	public FinancasFragmentPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int position) {
		FinancasFragment meuFragment = new FinancasFragment();
		
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
        String msg = "";
        switch (position) {
            case 0:
                msg = "Ganhos";
                break;
            case 1:
                msg = "Gastos";
                break;
            case 2:
                msg = "Totais";
                break;
            default:
                break;
        }
        return msg;
    }

}
