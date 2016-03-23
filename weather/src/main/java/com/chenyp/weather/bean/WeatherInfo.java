package com.chenyp.weather.bean;

import java.util.List;

public class WeatherInfo {

	private ResponseResult result;
	private String city;
	private String updateTime;
	private String temp;
	private String humidity;
	private String windD;
	private String windS;
	private String sunriseTime;
	private String sunsetTime;
	private Environment environment;
	private Alarm alarm;
	private List<Weather> weatherList;
	private List<IndexInfo> indexList;

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(String updateTime) {
		this.updateTime = updateTime;
	}

	public String getTemp() {
		return temp;
	}

	public void setTemp(String temp) {
		this.temp = temp;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWindD() {
		return windD;
	}

	public void setWindD(String windD) {
		this.windD = windD;
	}

	public String getWindS() {
		return windS;
	}

	public void setWindS(String windS) {
		this.windS = windS;
	}

	public String getSunriseTime() {
		return sunriseTime;
	}

	public void setSunriseTime(String sunriseTime) {
		this.sunriseTime = sunriseTime;
	}

	public String getSunsetTime() {
		return sunsetTime;
	}

	public void setSunsetTime(String sunsetTime) {
		this.sunsetTime = sunsetTime;
	}

	public Environment getEnvironment() {
		return environment;
	}

	public void setEnvironment(Environment environment) {
		this.environment = environment;
	}

	public Alarm getAlarm() {
		return alarm;
	}

	public void setAlarm(Alarm alarm) {
		this.alarm = alarm;
	}

	public List<Weather> getWeatherList() {
		return weatherList;
	}

	public void setWeatherList(List<Weather> weatherList) {
		this.weatherList = weatherList;
	}

	public List<IndexInfo> getIndexList() {
		return indexList;
	}

	public void setIndexList(List<IndexInfo> indexList) {
		this.indexList = indexList;
	}

}
