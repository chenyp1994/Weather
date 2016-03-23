package com.chenyp.jpzjweather.util.linstener;

/**
 * Created by chenyp on 15-6-15.
 */
public interface HttpCallbackListener {

    void onFinish(String response);

    void onError(Exception e);
}
