package br.com.fiap.financas.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import br.com.fiap.financas.activity.GanhosFragment;
import br.com.fiap.financas.activity.GastosFragment;
import br.com.fiap.financas.activity.TotalFragment;
 
public class TabsPagerAdapter extends FragmentPagerAdapter {
 
    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }
 
    @Override
    public Fragment getItem(int index) {
 
        switch (index) {
        case 0:
            // Top Rated fragment activity
            return new GanhosFragment();
        case 1:
            // Games fragment activity
            return new GastosFragment();
        case 2:
            // Movies fragment activity
            return new TotalFragment();
        }
 
        return null;
    }
 
    @Override
    public int getCount() {
        // get item count - equal to number of tabs
        return 3;
    }
 
}