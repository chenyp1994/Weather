package com.coolweather.app.util;

public interface HttpCallbackListener {

	/**
	 * HttpRequest Success
	 * @param response
	 */
	void onFinish(String response);

	/**
	 * HttpReques Error
	 * @param e
	 */
	void onError(Exception e);

}
