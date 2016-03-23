package com.chenyp.jpzjweather.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.adapter.WeatherPagerAdapter;
import com.chenyp.jpzjweather.ui.base.BaseActivity;
import com.chenyp.jpzjweather.ui.base.BaseFragment;
import com.chenyp.jpzjweather.ui.fragment.LocateFragment;
import com.chenyp.jpzjweather.ui.fragment.WeatherFragment;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import butterknife.Bind;
import butterknife.ButterKnife;
import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity /*implements View.OnClickListener*/ {

    public static final String TAG = "MainActivity";

    public static final int SELECT_CITY = 0;
    public static final String CITYLIST = "城市列表";

    private List<Fragment> fragmentList = new ArrayList<>();
    WeatherPagerAdapter weatherPagerAdapter;
    private ArrayList<String> cityList;

    /*private String[] citys = {"广州", "北京", "上海"};*/

    Drawable toolBarIcon;
    @Bind(R.id.id_main_toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.id_text_city)
    public TextView tvCity;
    @Bind(R.id.id_tv_current_time)
    public TextView currentTime;
    @Bind(R.id.indicator_default)
    public CircleIndicator circleIndicator;
    @Bind(R.id.id_viewPager_weather)
    public ViewPager mViewPager;
    @Bind(R.id.id_main_location_icon)
    public ImageView mainLocationIcon;
    /* bottom tab*/
    /*@Bind(R.id.id_tab_button_cloud)
    public LinearLayout tabCloud;*/
    /*@Bind(R.id.id_tab_button_hist)
    public LinearLayout tabHist;*/
    /*@Bind(R.id.id_tab_button_user)
    public LinearLayout tabUser;*/
    /*@Bind(R.id.id_tab_icon_cloud)
    public IconTextView iconCloud;*/
    /*@Bind(R.id.id_tab_icon_hist)
    public IconTextView iconHist;*/
    /*@Bind(R.id.id_tab_icon_user)
    public IconTextView iconUser;*/
    /*@Bind(R.id.id_tab_tv_weather_main)
    public TextView tvCloud;*/
    /*@Bind(R.id.id_tab_tv_weather_hist)
    public TextView tvHist;*/
    /*@Bind(R.id.id_tab_tv_user)
    public TextView tvUser;*/
    /*@Bind(R.id.id_tab_cloud_layout)
    public View cloudLayout;
    @Bind(R.id.id_tab_hist_layout)*/
    /*public View histLayout;*/
    /*@Bind(R.id.id_tab_user_layout)
    public View userLayout;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        /*tabCloud.setOnClickListener(this);
        tabHist.setOnClickListener(this);
        tabUser.setOnClickListener(this);*/

        initCloudLayout();
    }

    private void initCloudLayout() {
        LocateFragment locateFragment = LocateFragment.getInstance();
        fragmentList.add(locateFragment);
        //添加自行添加的WeatherFragment
        /*for (String tempCity : citys) {
            WeatherFragment weatherFragment = WeatherFragment.newInstance(tempCity);
            fragmentList.add(weatherFragment);
        }

        Collections.addAll(cityList, citys);*/
        LoadData();
        if(cityList.size() != 0) {
            for (String tempCity : cityList) {
                WeatherFragment weatherFragment = WeatherFragment.newInstance(tempCity);
                fragmentList.add(weatherFragment);
            }
            mViewPager.setAdapter(new WeatherPagerAdapter(getSupportFragmentManager(), fragmentList));
            circleIndicator.setViewPager(mViewPager);
        }

        toolBarIcon = new IconDrawable(this, Iconify.IconValue.fa_home)
                .colorRes(R.color.main_toolbar_color).sizeRes(R.dimen.toolbar_icon_size);
        mToolbar.setNavigationIcon(toolBarIcon);
        setSupportActionBar(mToolbar);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SelectedCityActivity.class);
                intent.putStringArrayListExtra(CITYLIST, cityList);
                startActivityForResult(intent, SELECT_CITY);
            }

        });

        weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), fragmentList);

        mViewPager.setAdapter(weatherPagerAdapter);
        mViewPager.setCurrentItem(0);
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mViewPager.addOnPageChangeListener(pageListener);
        circleIndicator.setViewPager(mViewPager);
    }

    private ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.i(TAG, "onPageScrollStateChanged");
            int position = mViewPager.getCurrentItem();

            switch (state) {
                // 正在拖动
                case ViewPager.SCROLL_STATE_DRAGGING:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_DRAGGING position:" + position);
                    break;
                // 拖动释放后正在沉降的过程
                case ViewPager.SCROLL_STATE_SETTLING:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_SETTLING position:" + position);
                    Fragment fragment = fragmentList.get(position);
                    Bundle bundle = fragment.getArguments();
                    String area = null;
                    if (bundle != null) {
                        area = bundle.getString(BaseFragment.AREA_NAME, "");
                    }
                    if (position == 0) {
                        mainLocationIcon.setVisibility(View.VISIBLE);
                    } else {
                        mainLocationIcon.setVisibility(View.GONE);
                    }
                    tvCity.setText(area);
                    currentTime.setText(new SimpleDateFormat("MM月dd日 EEEE").format(new Date()));
                    break;
                // 切换动画全部完成结束
                case ViewPager.SCROLL_STATE_IDLE:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_IDLE position:" + position);
                    break;
            }
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            cityList = data.getStringArrayListExtra(MainActivity.CITYLIST);
            fragmentList.clear();

            LocateFragment locateFragment = LocateFragment.getInstance();
            fragmentList.add(locateFragment);
            for (String tempCity : cityList) {
                WeatherFragment weatherFragment = WeatherFragment.newInstance(tempCity);
                fragmentList.add(weatherFragment);
            }
            weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), fragmentList);
            mViewPager.setAdapter(weatherPagerAdapter);
            circleIndicator.setViewPager(mViewPager);
        }
    }

    /*@Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.id_tab_button_cloud:
                cloudLayout.setVisibility(View.VISIBLE);
                iconCloud.setTextColor(getResources().getColor(R.color.bottom_tab_selected));
                tvCloud.setTextColor(getResources().getColor(R.color.bottom_tab_selected));

                histLayout.setVisibility(View.GONE);
                iconHist.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                tvHist.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));

                *//*userLayout.setVisibility(View.GONE);
                iconUser.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                tvUser.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));*//*
                break;
            case R.id.id_tab_button_hist:
                cloudLayout.setVisibility(View.GONE);
                tvCloud.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                iconCloud.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));

                histLayout.setVisibility(View.VISIBLE);
                iconHist.setTextColor(getResources().getColor(R.color.bottom_tab_selected));
                tvHist.setTextColor(getResources().getColor(R.color.bottom_tab_selected));

                *//*userLayout.setVisibility(View.GONE);
                iconUser.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                tvUser.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));*//*
                break;
            *//*case R.id.id_tab_button_user:
                cloudLayout.setVisibility(View.GONE);
                histLayout.setVisibility(View.GONE);
                userLayout.setVisibility(View.VISIBLE);
                iconCloud.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                iconHist.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                iconUser.setTextColor(getResources().getColor(R.color.bottom_tab_selected));
                tvCloud.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                tvHist.setTextColor(getResources().getColor(R.color.bottom_tab_unselected));
                tvUser.setTextColor(getResources().getColor(R.color.bottom_tab_selected));
                break;*//*
        }
    }*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        Drawable shareIcon = new IconDrawable(this, Iconify.IconValue.fa_share_alt)
                .colorRes(R.color.main_toolbar_color).sizeRes(R.dimen.toolbar_icon_size);
        menu.findItem(R.id.action_share).setIcon(shareIcon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_share) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void SaveData() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences(TAG, MODE_PRIVATE);
        SharedPreferences.Editor edit = share.edit(); //编辑文件
        Set<String> cityListSet = new HashSet();
        cityListSet.addAll(cityList);
        edit.putStringSet("cityList", cityListSet);
        edit.apply();  //保存数据信息
    }

    public void LoadData() {
        //指定操作的文件名称
        SharedPreferences share = getSharedPreferences(TAG, MODE_PRIVATE);
        Set<String> cityListSet;
        cityListSet = share.getStringSet("cityList", null);
        cityList = new ArrayList<>();
        if(cityListSet != null) {
            cityList.addAll(cityListSet);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SaveData();
    }
}
