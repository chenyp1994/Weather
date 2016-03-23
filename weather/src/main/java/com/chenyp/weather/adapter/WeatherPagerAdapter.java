package com.chenyp.weather.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.chenyp.weather.ui.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chenyp on 15-6-17.
 */
public class WeatherPagerAdapter extends FragmentStatePagerAdapter {

    private int pagerCount = 5;

    private List<WeatherFragment> fragmentList;

    private FragmentManager fm;

    public WeatherPagerAdapter(FragmentManager fm, List<WeatherFragment> fragmentList) {
        super(fm);
        this.fragmentList = fragmentList;
        this.fm = fm;
    }

    @Override
    public WeatherFragment getItem(int position) {
        return fragmentList.get(position);
    }

    public void setFragments(ArrayList<WeatherFragment> fragmentList) {
        if (this.fragmentList != null) {
            FragmentTransaction ft = fm.beginTransaction();
            for (WeatherFragment f : this.fragmentList) {
                ft.remove(f);
            }
            ft.commit();
            ft = null;
            fm.executePendingTransactions();
        }
        this.fragmentList = fragmentList;
        notifyDataSetChanged();
    }

    public void addWeatherFragment(WeatherFragment fragment) {
        fragmentList.add(fragment);
    }

    public void deleteWeatherFragment(WeatherFragment fragment) {
        fragmentList.remove(fragment);
    }

    @Override
    public int getCount() {
        return fragmentList.size();
    }

    @Override
    public Object instantiateItem(View container, int position) {
        // TODO Auto-generated method stub
        return super.instantiateItem(container, position);
    }
}
