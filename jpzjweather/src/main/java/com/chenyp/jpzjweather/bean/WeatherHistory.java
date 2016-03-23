package com.chenyp.jpzjweather.bean;

import java.util.List;

public class WeatherHistory {

	private ResponseResult result;
	private List<TWeatherHistory> weatherHistory;

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

	public List<TWeatherHistory> getWeatherHistory() {
		return weatherHistory;
	}

	public void setWeatherHistory(List<TWeatherHistory> weatherHistory) {
		this.weatherHistory = weatherHistory;
	}

}
