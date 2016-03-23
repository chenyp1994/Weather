package com.chenyp.weather.ui.activity;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.chenyp.weather.R;
import com.chenyp.weather.adapter.WeatherPagerAdapter;
import com.chenyp.weather.ui.animation.WeatherPageTransformer;
import com.chenyp.weather.ui.fragment.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import me.relex.circleindicator.CircleIndicator;

public class MainActivity extends BaseActivity {

    public static final String TAG = "MainActivity";
    private ViewPager mViewPager;
    private List<WeatherFragment> fragmentList = new ArrayList<>();
    private WeatherPagerAdapter weatherPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initEvent();
        init();
    }

    @Override
    public void initView() {
        mViewPager = (ViewPager) findViewById(R.id.id_viewPager_weather);
    }

    @Override
    public void initEvent() {

    }

    @Override
    public void init() {
        CircleIndicator circleIndicator = (CircleIndicator) findViewById(R.id.indicator_default);
        for (int i = 0; i < 5; i++) {
            fragmentList.add(WeatherFragment.newInstance("南海"));
        }
        weatherPagerAdapter = new WeatherPagerAdapter(getSupportFragmentManager(), fragmentList);
        mViewPager.setAdapter(weatherPagerAdapter);
        circleIndicator.setViewPager(mViewPager);
        mViewPager.setPageTransformer(true, new WeatherPageTransformer());
        mViewPager.setCurrentItem(0);
        // 预加载第一个
        mViewPager.setOffscreenPageLimit(fragmentList.size());
        mViewPager.addOnPageChangeListener(pageListener);
    }

    public ViewPager.OnPageChangeListener pageListener = new ViewPager.OnPageChangeListener() {

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
            ArrayList hasLoadedPages = new ArrayList<Integer>();

            switch (state) {
                // 正在拖动
                case ViewPager.SCROLL_STATE_DRAGGING:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_DRAGGING position:" + position);
                    break;
                // 拖动释放后正在沉降的过程
                case ViewPager.SCROLL_STATE_SETTLING:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_SETTLING position:" + position);
                    break;
                // 切换动画全部完成结束
                case ViewPager.SCROLL_STATE_IDLE:
                    Log.i(TAG, "ViewPager.SCROLL_STATE_IDLE position:" + position);
                    break;
            }
        }
    };

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
