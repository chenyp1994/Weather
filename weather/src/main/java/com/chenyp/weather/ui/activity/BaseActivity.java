package com.chenyp.weather.ui.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.chenyp.weather.util.ActivityCollector;

/**
 * Created by chenyp on 15-6-15.
 */
public abstract class BaseActivity extends AppCompatActivity {

    public final static String TAG = "BaseActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCollector.addActivity(this);
        if(getSupportActionBar() != null)
            getSupportActionBar().hide();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }

    public abstract void initView();
    public abstract void initEvent();
    public abstract void init();

}
