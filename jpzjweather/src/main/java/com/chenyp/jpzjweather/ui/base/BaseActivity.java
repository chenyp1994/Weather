package com.chenyp.jpzjweather.ui.base;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.chenyp.jpzjweather.util.ActivityCollector;
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

}
