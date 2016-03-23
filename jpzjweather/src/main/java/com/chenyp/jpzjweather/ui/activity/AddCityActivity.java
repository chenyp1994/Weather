package com.chenyp.jpzjweather.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.adapter.hostCityRecyclerViewAdapter;
import com.chenyp.jpzjweather.bean.Area;
import com.chenyp.jpzjweather.db.AreaDB;
import com.chenyp.jpzjweather.ui.base.BaseActivity;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.animators.FadeInAnimator;

/**
 * Created by chenyp on 15-7-7.
 */
public class AddCityActivity extends BaseActivity {

    @Bind(R.id.id_toolbar_search_city)
    public Toolbar mToolbar;
    @Bind(R.id.id_gv_host_city_list)
    public RecyclerView hostList;
    @Bind(R.id.search_list)
    public ListView mSearchListView;
    @Bind(R.id.id_search_box)
    public EditText mEditText;

    private InputMethodManager mInputMethodManager;

    private List<Area> searchList;

    private AreaDB areaDB = AreaDB.getInstance(this);

    public static final String ADD_CITY = "添加的城市";

    List<String> hostCityList;
    //已经选择的city
    ArrayList<String> sCityList;
    SimpleCursorAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_city);
        ButterKnife.bind(this);

        Intent intent = getIntent();

        sCityList = intent.getStringArrayListExtra(MainActivity.CITYLIST);

        Drawable toolBarIcon = new IconDrawable(this, Iconify.IconValue.fa_arrow_left)
                .colorRes(R.color.main_toolbar_color).sizeRes(R.dimen.toolbar_icon_size);
        mToolbar.setNavigationIcon(toolBarIcon);
        mToolbar.setTitle("添加城市");
        setSupportActionBar(mToolbar);

        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                AddCityActivity.this.finish();
            }

        });

        hostList.setLayoutManager(new GridLayoutManager(this, 3));
        // Item Decorator:
        hostList.setItemAnimator(new FadeInAnimator());

        String[] cityList = getResources().getStringArray(R.array.host_city_list);
        hostCityList = Arrays.asList(cityList);
        hostCityRecyclerViewAdapter mAdapter = new hostCityRecyclerViewAdapter(AddCityActivity.this, hostCityList
                , R.layout.host_city_item);
        hostList.setAdapter(mAdapter);
        mEditText.addTextChangedListener(watcher);
        /*mSearchListView.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                mInputMethodManager.hideSoftInputFromWindow(
                        mEditText.getWindowToken(), 0);
                return true;
            }
        });*/
        mSearchListView
                .setOnItemClickListener(new android.widget.AdapterView.OnItemClickListener() {

                    @Override
                    public void onItemClick(AdapterView<?> parent, View view,
                                            int position, long id) {
                        String city = searchList.get(position).getNamecn();
                        ArrayList<String> sCityListTemp = new ArrayList<>();
                        sCityListTemp.addAll(sCityList);
                        if (!sCityListTemp.remove(city)) {
                            Intent intent = new Intent();
                            intent.putExtra(AddCityActivity.ADD_CITY, city);
                            AddCityActivity.this.setResult(Activity.RESULT_OK, intent);
                            AddCityActivity.this.finish();
                        } else {
                            Toast.makeText(AddCityActivity.this, "不能添加重复城市", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }

    public ArrayList<String> getSCityList() {
        return sCityList;
    }

    TextWatcher watcher = new TextWatcher() {
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            Cursor cursor;
            String text = mEditText.getText().toString();
            if (text.equals("")) {
                hostList.setVisibility(View.VISIBLE);
                mSearchListView.setVisibility(View.GONE);
            } else {
                hostList.setVisibility(View.GONE);
                mSearchListView.setVisibility(View.VISIBLE);
            }
            cursor = areaDB.getAreaByNamecn(text);
            if (cursor == null) {
                cursor = areaDB.getAreaByNameen(text);
            }
            if (cursor == null) {
                return;
            }
            searchList = new ArrayList<Area>();
            if (cursor.moveToFirst()) {
                do {
                    Area area = new Area();
                    area.setId(cursor.getInt(cursor.getColumnIndex("_id")));
                    area.setAreaid(cursor.getLong(cursor.getColumnIndex("areaid")));
                    area.setNamecn(cursor.getString(cursor
                            .getColumnIndex("namecn")));
                    area.setNameen(cursor.getString(cursor
                            .getColumnIndex("nameen")));
                    area.setDistrictcn(cursor.getString(cursor
                            .getColumnIndex("districtcn")));
                    area.setDistricten(cursor.getString(cursor
                            .getColumnIndex("districten")));
                    area.setProvincecn(cursor.getString(cursor
                            .getColumnIndex("provincecn")));
                    area.setProvinceen(cursor.getString(cursor.getColumnIndex("provinceen")));
                    area.setNationcn(cursor.getString(cursor.getColumnIndex("nationcn")));
                    area.setNationen(cursor.getString(cursor.getColumnIndex("nationen")));
                    searchList.add(area);
                } while (cursor.moveToNext());
            }
            mAdapter = new SimpleCursorAdapter(AddCityActivity.this,
                    android.R.layout.simple_list_item_2, cursor, new String[]{"namecn", "provincecn"},
                    new int[]{android.R.id.text1, android.R.id.text2});
            mSearchListView.setAdapter(mAdapter);
        }

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                                      int after) {
            Log.d("===============", "beforTextChanged is called");
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("=================", "afterTextChanged is called!");
        }

    };
}

