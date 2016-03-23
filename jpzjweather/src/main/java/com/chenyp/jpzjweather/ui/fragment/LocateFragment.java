package com.chenyp.jpzjweather.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.IconTextView;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.location.LocationManagerProxy;
import com.amap.api.location.LocationProviderProxy;
import com.chenyp.jpzjweather.R;
import com.chenyp.jpzjweather.adapter.IndexRecyclerViewAdapter;
import com.chenyp.jpzjweather.bean.FurWeather24;
import com.chenyp.jpzjweather.bean.WeatherInfo;
import com.chenyp.jpzjweather.ui.activity.MainActivity;
import com.chenyp.jpzjweather.ui.base.BaseFragment;
import com.chenyp.jpzjweather.util.HttpUtil;
import com.chenyp.jpzjweather.util.IconUtil;
import com.chenyp.jpzjweather.util.NetworkUtil;
import com.chenyp.jpzjweather.util.linstener.HttpCallbackListener;
import com.github.mikephil.charting.charts.LineChart;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.joanzapata.android.iconify.IconDrawable;
import com.joanzapata.android.iconify.Iconify;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chenyp on 15-6-16.
 */
public class LocateFragment extends BaseFragment {

    public static final String TAG = "LocateFragment";

    private volatile static LocateFragment locateFragment;

    LocationManagerProxy mLocationManagerProxy;

    private Gson gson;
    private DisplayImageOptions options;
    private String area = "";
    private Activity mActivity;

    //对象数据
    private WeatherInfo weatherInfo;
    private FurWeather24 furWeather24;

    private final static int MSG_GET_WEATHER_SUCCESS = 1;
    private final static int MSG_GET_CUR24_SUCCESS = 2;
    private final static int MSG_NET_NO_CONNECT = 3;
    private final static int MSG_GET_ERROR = 4;

    /*//绘图
    @Bind(R.id.id_lineChart_further_days)
    public LineChart furdaysLineChart;
    @Bind(R.id.id_lineChart_further24h)
    public LineChart fur24hLineChart;*/

    private int[] aqiColors = {R.color.aqi_excellent, R.color.aqi_good,
            R.color.aqi_mild_pollution, R.color.aqi_moderate_pollution,
            R.color.aqi_high_pollution, R.color.aqi_serious_pollution};

    //View
    @Bind(R.id.id_tv_api)
    public TextView aqi;
    @Bind(R.id.id_icon_aqi)
    public IconTextView aqiIcon;
    @Bind(R.id.id_tv_updateTime)
    public TextView updateTime;
    @Bind(R.id.id_tv_curTemp)
    public TextView curTemp;
    @Bind(R.id.id_tv_weather)
    public TextView weather;
    @Bind(R.id.id_tv_temperature)
    public TextView temperature;
    @Bind(R.id.id_tv_windD)
    public TextView windD;
    @Bind(R.id.id_tv_windS)
    public TextView windS;
    @Bind(R.id.id_rv_weather_index)
    public RecyclerView rvIndex;
    @Bind(R.id.id_img_weather_icon)
    public ImageView imgWeatherIcon;
    @Bind(R.id.pull_to_refresh_scrollView)
    public PullToRefreshScrollView pullToRefreshScrollView;


    private Handler handler = new Handler(new Handler.Callback() {

        @Override
        public boolean handleMessage(Message msg) {
            // TODO Auto-generated method stub
            switch (msg.what) {
                case MSG_GET_WEATHER_SUCCESS:
                    Log.i(TAG, weatherInfo.toString());
                    pullToRefreshScrollView.onRefreshComplete();
                    updateTodayInfo();
                    break;
                case MSG_GET_CUR24_SUCCESS:
                    //Log.i(TAG, furWeather24.toString());
                    pullToRefreshScrollView.onRefreshComplete();
                    break;
                case MSG_NET_NO_CONNECT:
                    pullToRefreshScrollView.onRefreshComplete();
                    break;
                case MSG_GET_ERROR:
                    pullToRefreshScrollView.onRefreshComplete();
                    break;

                default:

                    break;
            }
            return true;
        }

    });

    public static LocateFragment getInstance() {
        if (locateFragment == null) {
            synchronized (LocateFragment.class) {
                if (locateFragment == null) {
                    locateFragment = new LocateFragment();
                    locateFragment.setArguments(new Bundle());
                }
            }
        }
        return locateFragment;
    }

    @Override
    public void onAttach(Activity activity) {
        this.mActivity = activity;
        super.onAttach(activity);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd").create();
        options = new DisplayImageOptions.Builder()
                .showImageOnFail(R.drawable.undefined)
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        initLocationSetting();
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getArguments() != null) {
            area = getArguments().getString(AREA_NAME);
        }
        if (isVisibleToUser) {
            //fragment可见时加载数据
            if (area != null && !area.equals("")) {
                getWeatherData(area);
            }
            ((MainActivity) mActivity).tvCity.setText(area);
            ((MainActivity) mActivity).currentTime.setText(new SimpleDateFormat("MM月dd日 EEEE").format(new Date()));
        } else {
            //fragment不可见时不执行操作
            Log.i(TAG, "fragment不可见");
        }
    }

    public void getWeatherData(String city) {
        if (!NetworkUtil.isConnectInternet(mActivity)) {
            Log.i(TAG, "ConnectInternet");
            return;
        }
        TelephonyManager tm = (TelephonyManager) mActivity.getSystemService(Context.TELEPHONY_SERVICE);
        String meid = tm.getDeviceId();
        try {
            city = URLEncoder.encode(city, "UTF-8");
            meid = URLEncoder.encode(meid, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String address;
        address = getString(R.string.service_weatherData_address);

        HttpUtil.sendHttpRequest(address + city + "&meid=" + meid, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i(TAG, response);
                weatherInfo = gson.fromJson(response, WeatherInfo.class);
                if (weatherInfo.getResult().getError().equals("0")) {
                    handler.obtainMessage(MSG_GET_WEATHER_SUCCESS).sendToTarget();
                } else {
                    handler.obtainMessage(MSG_GET_ERROR).sendToTarget();
                }
            }

            @Override
            public void onError(Exception e) {
                Message msg = new Message();
                msg.what = MSG_GET_ERROR;
                msg.obj = e;
                handler.sendMessage(msg);
            }
        });

        address = getString(R.string.service_furWeather24_address);
        HttpUtil.sendHttpRequest(address + city, new HttpCallbackListener() {
            @Override
            public void onFinish(String response) {
                Log.i(TAG, response);
                furWeather24 = gson.fromJson(response, FurWeather24.class);
                if (furWeather24.getResult().getError().equals("0")) {
                    handler.obtainMessage(MSG_GET_CUR24_SUCCESS).sendToTarget();
                } else {
                    handler.obtainMessage(MSG_GET_ERROR).sendToTarget();
                }
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
        ButterKnife.bind(this, v);
        rvIndex.setFocusable(false);
        rvIndex.setHasFixedSize(true);
        rvIndex.setLayoutManager(new GridLayoutManager(mActivity, 2));
        rvIndex.setItemAnimator(new DefaultItemAnimator());
        Drawable prtIcon = new IconDrawable(mActivity, Iconify.IconValue.fa_cloud)
                .colorRes(R.color.main_toolbar_color);
        pullToRefreshScrollView.setDividerDrawable(prtIcon);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {

            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                if (getArguments() != null) {
                    area = getArguments().getString(AREA_NAME);

                }
                if (area != null && !area.equals("")) {
                    getWeatherData(area);
                }
            }

        });
        return v;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    public void updateTodayInfo() {
        rvIndex.setItemViewCacheSize(weatherInfo.getIndexList().size());
        rvIndex.setAdapter(new IndexRecyclerViewAdapter(weatherInfo.getIndexList(),
                R.layout.weather_index_item));

        int tempAqi = Integer.parseInt(weatherInfo.getEnvironment().getAqi());
        aqi.setText(weatherInfo.getEnvironment().getAqi() +
                " " + weatherInfo.getEnvironment().getQuality());

        int j = 50;
        for (int aqiColor : aqiColors) {
            if (tempAqi <= j) {
                aqiIcon.setTextColor(getResources().getColor(aqiColor));
                break;
            }
            j += 50;
        }

        updateTime.setText(weatherInfo.getUpdateTime() + "发布");
        curTemp.setText(weatherInfo.getTemp() + "℃");
        /*imgWeatherIcon.setImageResource(IconUtil.getDIconId(
                weatherInfo.getWeatherList().get(1).getDayWeatherType()));*/
        //利用框架解决OOM的问题
        String imageUri = "drawable://" + IconUtil.getDIconId(
                weatherInfo.getWeatherList().get(1).getDayWeatherType());
        ImageLoader.getInstance().displayImage(imageUri, imgWeatherIcon, options);
        weather.setText(weatherInfo.getWeatherList().get(1).getDayWeatherType());
        temperature.setText(weatherInfo.getWeatherList().get(1).getMinTemp() +
                " ~ " + weatherInfo.getWeatherList().get(1).getMaxTemp() + "℃");
        windD.setText(weatherInfo.getWindD());
        windS.setText(weatherInfo.getWindS());
    }

    //高德地图
    public void initLocationSetting() {
        // 初始化定位，只采用网络定位
        mLocationManagerProxy = LocationManagerProxy.getInstance(mActivity);
        mLocationManagerProxy.setGpsEnable(false);
        // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
        // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用removeUpdates()方法来取消定位请求
        // 在定位结束后，在合适的生命周期调用destroy()方法
        // 其中如果间隔时间为-1，则定位只定一次,
        // 在单次定位情况下，定位无论成功与否，都无需调用removeUpdates()方法移除请求，定位sdk内部会移除
        mLocationManagerProxy.requestLocationData(
                LocationProviderProxy.AMapNetwork, 60 * 1000, 15, new AMapLocationListener() {
                    @Override
                    public void onLocationChanged(final AMapLocation aMapLocation) {
                        if (aMapLocation != null
                                && aMapLocation.getAMapException().getErrorCode() == 0) {
                            // 定位成功回调信息，设置相关消息
                            Bundle args = locateFragment.getArguments();
                            final String area = aMapLocation.getDistrict().substring(0, aMapLocation.getDistrict().length() - 1);
                            args.putString(BaseFragment.AREA_NAME, area);
                            getWeatherData(area);
                            new Handler().post(new Runnable() {
                                @Override
                                public void run() {
                                    if (((MainActivity) mActivity).mViewPager.getCurrentItem() == 0) {
                                        ((MainActivity) mActivity).mainLocationIcon.setVisibility(View.VISIBLE);
                                        ((MainActivity) mActivity).tvCity.setText(area);
                                        ((MainActivity) mActivity).currentTime.setText(new SimpleDateFormat("MM月dd日 EEEE").format(new Date()));
                                    }
                                }
                            });

                        } else {
                            Log.e("AmapErr", "Location ERR:" + aMapLocation.getAMapException().getErrorCode());
                        }
                    }

                    @Override
                    public void onLocationChanged(Location location) {

                    }

                    @Override
                    public void onStatusChanged(String provider, int status, Bundle extras) {

                    }

                    @Override
                    public void onProviderEnabled(String provider) {

                    }

                    @Override
                    public void onProviderDisabled(String provider) {

                    }
                }

        );
    }
}

