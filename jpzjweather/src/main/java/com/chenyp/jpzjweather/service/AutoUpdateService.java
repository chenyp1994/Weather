package com.chenyp.jpzjweather.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.util.Log;

import com.chenyp.jpzjweather.receiver.AutoUpdateReceiver;
import com.chenyp.jpzjweather.util.HttpUtil;
import com.chenyp.jpzjweather.util.linstener.HttpCallbackListener;

public class AutoUpdateService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		final HttpCallbackListener callbackListener = new HttpCallbackListener() {
			@Override
			public void onFinish(String response) {

			}

			@Override
			public void onError(Exception e) {

			}
		};
		new Thread(new Runnable() {
			@Override
			public void run() {
				updateWeather(callbackListener);
			}
		}).start();
		AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
		//int anHour = 8 * 60 * 60 * 1000; // 这是8小时的毫秒数
		int anHour = 60 * 1000; // 这是1小时的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime() + anHour;
		Intent i = new Intent(this, AutoUpdateReceiver.class);
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		return super.onStartCommand(intent, flags, startId);
	}
	
	/**
	 * 更新天气信息。
	 */
	private void updateWeather(HttpCallbackListener callbackListener) {
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
		String weatherCode = prefs.getString("weather_code", "");
		String address = "http://www.weather.com.cn/data/cityinfo/" + weatherCode + ".html";
		HttpUtil.sendHttpRequest(address, callbackListener);
	}

}
