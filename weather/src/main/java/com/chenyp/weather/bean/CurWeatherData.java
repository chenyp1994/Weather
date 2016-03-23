package com.chenyp.weather.bean;

import java.util.ArrayList;

/**
 * Created by chenyp on 15-6-16.
 */
public class CurWeatherData {
    /**
     * Current Area
     */
    private String currentCity;

    /**
     * pm 2.5
     */
    private String pm25;

    /**
     * CurWeatherIndex of Living ArrayList
     */
    private ArrayList<CurWeatherIndex> index;

    public String getCurrentCity() {
        return currentCity;
    }

    public void setCurrentCity(String currentCity) {
        this.currentCity = currentCity;
    }

    public String getPm25() {
        return pm25;
    }

    public void setPm25(String pm25) {
        this.pm25 = pm25;
    }

    public ArrayList<CurWeatherIndex> getIndex() {
        return index;
    }

    public void setIndex(ArrayList<CurWeatherIndex> index) {
        this.index = index;
    }
}