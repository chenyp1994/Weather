package com.chenyp.weather.ui.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chenyp.weather.R;
import com.chenyp.weather.adapter.IndexRecyclerViewAdapter;
import com.chenyp.weather.bean.Environment;
import com.chenyp.weather.bean.FurWeather24;
import com.chenyp.weather.bean.WeatherInfo;
import com.chenyp.weather.util.HttpUtil;
import com.chenyp.weather.util.linstener.HttpCallbackListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**
 * Created by chenyp on 15-6-16.
 */
public class WeatherFragment extends Fragment {

    public static final String TAG = "WeatherFragment";

    private static final String AREA_NAME = "area_name";
    private Gson gson;
    private WeatherInfo weatherInfo;
    private FurWeather24 furWeather24;
    private IndexRecyclerViewAdapter rvIndexAdapter;

    private final static int MSG_GET_WEATHER_SUCCESS = 1;
    private final static int MSG_GET_CUR24_SUCCESS = 2;
    private final static int MSG_NET_NO_CONNECT = 3;
    private final static int MSG_GET_ERROR = 4;

    //绘图
    /*private LineChart fur7dLineChart;
    private LineChart fur24hLineChart;*/

    //信息
    public TextView aqi;
    private TextView updateTime;
    private TextView curTemp;
    private TextView weather;
    private TextView temperature;
    private TextView windD;
    private TextView windS;

    //指数
    private RecyclerView rvIndex;
    private IndexRecyclerViewAdapter mRvAdatper;

    private String area = "南海";
    private Activity mActivity;

    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_GET_WEATHER_SUCCESS:
                    Log.i(TAG, weatherInfo.toString());
                    rvIndex.setHasFixedSize(true);
                    rvIndex.setLayoutManager(new GridLayoutManager(mActivity, 2));
                    rvIndex.setItemAnimator(new DefaultItemAnimator());
                    rvIndexAdapter = new IndexRecyclerViewAdapter(mActivity, weatherInfo.getIndexList());
                    rvIndex.setAdapter(rvIndexAdapter);

                    aqi.setText(weatherInfo.getEnvironment().getAqi() +
                            " " + weatherInfo.getEnvironment().getQuality());
                    updateTime.setText(weatherInfo.getUpdateTime() + "发布");
                    curTemp.setText(weatherInfo.getTemp() + "℃");
                    weather.setText(weatherInfo.getWeatherList().get(1).getDayWeatherType());
                    temperature.setText(weatherInfo.getWeatherList().get(1).getMinTemp() +
                            " ~ " + weatherInfo.getWeatherList().get(1).getMaxTemp() + "℃");
                    windD.setText(weatherInfo.getWindD());
                    windS.setText(weatherInfo.getWindS());
                    break;
                case MSG_GET_CUR24_SUCCESS:
                    Log.i(TAG, furWeather24.toString());
                    break;
                case MSG_NET_NO_CONNECT:

                    break;
                case MSG_GET_ERROR:

                    break;
                default:
                    break;
            }
            return true;
        }

    });

    public static WeatherFragment newInstance(String area) {
        WeatherFragment fragment = new WeatherFragment();
        Bundle args = new Bundle();
        args.putString(AREA_NAME, area);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            area = getArguments().getString(AREA_NAME);
        }
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        mActivity = getActivity();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {
            //fragment可见时加载数据
            if (weatherInfo != null && furWeather24 != null) {
                handler.obtainMessage(MSG_GET_WEATHER_SUCCESS).sendToTarget();
                handler.obtainMessage(MSG_GET_CUR24_SUCCESS).sendToTarget();
            } else {
                getWeatherData(area);
            }
        } else {
            //fragment不可见时不执行操作
        }
    }

    private void getWeatherData(String city) {
        String address;
        address = getString(R.string.service_weatherData_address);
        HttpUtil.sendHttpRequest(address + city, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i(TAG, response);
                weatherInfo = gson.fromJson(response, WeatherInfo.class);
                handler.obtainMessage(MSG_GET_WEATHER_SUCCESS).sendToTarget();
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = MSG_GET_ERROR;
                msg.obj = e;
                handler.sendMessage(msg);
            }
        });

        address = getString(R.string.service_furtWeather24_address);
        HttpUtil.sendHttpRequest(address + city, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i(TAG, response);
                furWeather24 = gson.fromJson(response, FurWeather24.class);
                handler.obtainMessage(MSG_GET_CUR24_SUCCESS).sendToTarget();
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = MSG_GET_ERROR;
                msg.obj = e;
                handler.sendMessage(msg);
            }
        });

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.weather_fragment, container, false);
        rvIndex = (RecyclerView) v.findViewById(R.id.id_gv_weather_index);
        /*fur24hLineChart = (LineChart) v.findViewById(R.id.id_lineChart_further24h);
        fur7dLineChart = (LineChart) v.findViewById(R.id.id_lineChart_further7d);*/
        aqi = (TextView) v.findViewById(R.id.id_tv_api);
        updateTime = (TextView) v.findViewById(R.id.id_tv_updateTime);
        curTemp = (TextView) v.findViewById(R.id.id_tv_curTemp);
        weather = (TextView) v.findViewById(R.id.id_tv_weather);
        temperature = (TextView) v.findViewById(R.id.id_tv_temperature);
        windD = (TextView) v.findViewById(R.id.id_tv_windD);
        windS = (TextView) v.findViewById(R.id.id_tv_windS);
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    /* 摧毁视图 */
    @Override
    public void onDestroyView() {
        // TODO Auto-generated method stub
        super.onDestroyView();
    }

    /* 摧毁该Fragment，一般是FragmentActivity 被摧毁的时候伴随着摧毁 */
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

}
