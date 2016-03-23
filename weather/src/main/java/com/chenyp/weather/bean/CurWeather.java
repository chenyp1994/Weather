package com.chenyp.weather.bean;

/**
 * Weather Response
 * Created by chenyp on 15-6-15.
 */
public class CurWeather {
    /**
     * Error
     */
    private String error;

    /**
     * HttpResponse Status
     */
    private String status;

    /**
     * Current Date
     */
    private String date;

    /**
     * Weather Results
     */
    private CurWeatherData curWeatherData;


    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public CurWeatherData getCurWeatherData() {
        return curWeatherData;
    }

    public void setCurWeatherData(CurWeatherData curWeatherData) {
        this.curWeatherData = curWeatherData;
    }
}
