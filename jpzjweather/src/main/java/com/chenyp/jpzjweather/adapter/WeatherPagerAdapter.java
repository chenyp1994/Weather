package com.chenyp.jpzjweather.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.ViewGroup;
import java.util.List;

/**
 * Created by chenyp on 15-6-17.
 */
public class WeatherPagerAdapter extends FragmentPagerAdapter {

    private int pagerCount = 5;

    private List<Fragment> fragmentList;

    private FragmentManager fm;

    public WeatherPagerAdapter(FragmentManager fm, List<Fragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int position) {
        return fragmentList.get(position);
    }

    @Override
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    public void setFragments(List<Fragment> fragmentList) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (Fragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void addWeatherFragment(Fragment fragment) {
        fragmentList.add(fragment);
    }

    public void deleteWeatherFragment(Fragment fragment) {
        fragmentList.remove(fragment);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        Object obj = super.instantiateItem(container, position);
        return obj;
    }

}
