package com.chenyp.jpzjweather.bean;

import java.util.List;

public class FurWeather24 {

	private ResponseResult result;
	private List<Data24> data24;

	public ResponseResult getResult() {
		return result;
	}

	public void setResult(ResponseResult result) {
		this.result = result;
	}

	public List<Data24> getData24() {
		return data24;
	}

	public void setData24(List<Data24> data24) {
		this.data24 = data24;
	}

}
