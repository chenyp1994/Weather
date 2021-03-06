package com.chenyp.jpzjweather.util;

/**
 * Created by chenyp on 15-7-2.
 */

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

    /**
     * 检测是否已经连接网络。
     *
     * @param context
     * @return 当且仅当连上网络时返回true, 否则返回false。
     */

    public static boolean isConnectInternet(Context context) {

        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivityManager == null) {
            return false;
        }
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        return (info != null) && info.isAvailable();
    }

}
