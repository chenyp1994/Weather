package com.chenyp.jpzjweather.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.adapter.CityRecyclerViewAdapter;
import com.chenyp.jpzjweather.ui.base.BaseActivity;
import com.daimajia.swipe.util.Attributes;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by chenyp on 15-7-6.
 */
public class SelectedCityActivity extends BaseActivity {

    @Bind(R.id.id_select_toolbar)
    public Toolbar mToolbar;
    @Bind(R.id.id_recyclerView_cityList)
    public RecyclerView rvCityList;

    private ArrayList<String> cityList;
    private CityRecyclerViewAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_list);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        cityList = intent.getStringArrayListExtra(MainActivity.CITYLIST);

        Drawable toolBarIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_left)
                .colorRes(R.color.main_toolbar_color).sizeRes(R.dimen.toolbar_icon_size);
        mToolbar.setNavigationIcon(toolBarIcon);
        mToolbar.setTitle("城市列表");
        setSupportActionBar(mToolbar);

        // Layout Managers:
        rvCityList.setLayoutManager(new LinearLayoutManager(this));
        // Item Decorator:
        rvCityList.setItemAnimator(new FadeInAnimator());
        mAdapter = new CityRecyclerViewAdapter(cityList, R.layout.city_list_item);
        mAdapter.setMode(Attributes.Mode.Single);
        rvCityList.setAdapter(mAdapter);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putStringArrayListExtra(MainActivity.CITYLIST, mAdapter.getmList());
                setResult(Activity.RESULT_OK, intent);
                SelectedCityActivity.this.finish();
            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == Activity.RESULT_OK){
            String addCity = data.getStringExtra(AddCityActivity.ADD_CITY);
            mAdapter.getmList().add(addCity);
            mAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            //这里写你要在用户按下返回键同时执行的动作
            Intent intent = new Intent();
            intent.putStringArrayListExtra(MainActivity.CITYLIST, mAdapter.getmList());
            setResult(Activity.RESULT_OK, intent);
            SelectedCityActivity.this.finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_select, menu);
        Drawable shareIcon = new IconDrawable(this, Iconify.IconValue.fa_plus)
                .colorRes(R.color.main_toolbar_color).sizeRes(R.dimen.toolbar_icon_size);
        menu.findItem(R.id.action_add_city).setIcon(shareIcon);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_add_city) {
            Intent intent = new Intent(SelectedCityActivity.this, AddCityActivity.class);
            intent.putStringArrayListExtra(MainActivity.CITYLIST, cityList);
            startActivityForResult(intent, MainActivity.SELECT_CITY);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
