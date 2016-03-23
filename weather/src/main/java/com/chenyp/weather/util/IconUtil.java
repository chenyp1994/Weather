package com.chenyp.weather.util;

import com.chenyp.weather.R;

import java.util.HashMap;

/**
 * Created by chenyp on 15-6-29.
 */
public class IconUtil {

    public static HashMap<String, Integer> dIconList = new HashMap<String, Integer>();
    public static HashMap<String, Integer> nIconList = new HashMap<String, Integer>();
    public static String[] weatherType = {"晴", "多云", "阴", "阵雨", "雷阵雨",
            "雷阵雨伴有冰雹", "雨夹雪", "小雨", "中雨", "大雨", "暴雨", "大暴雨", "特大暴雨", "阵雪",
            "小雪", "中雪", "大雪", "暴雪", "雾", "冰雨", "沙尘暴", "小到中雨", "中到大雨", "大到暴雨",
            "暴到大暴雨", "大暴到特大暴雨", "小到中雪", "中到大雪", "大到暴雪", "沙尘", "扬沙", "强沙尘暴",};

    static {
        //加载白天天气的图标
        dIconList.put(weatherType[0], R.drawable.d00);
        dIconList.put(weatherType[1], R.drawable.d01);
        dIconList.put(weatherType[2], R.drawable.d02);
        dIconList.put(weatherType[3], R.drawable.d03);
        dIconList.put(weatherType[4], R.drawable.d04);
        dIconList.put(weatherType[5], R.drawable.d05);
        dIconList.put(weatherType[6], R.drawable.d06);
        dIconList.put(weatherType[7], R.drawable.d07);
        dIconList.put(weatherType[8], R.drawable.d08);
        dIconList.put(weatherType[9], R.drawable.d09);
        dIconList.put(weatherType[10], R.drawable.d10);
        dIconList.put(weatherType[11], R.drawable.d11);
        dIconList.put(weatherType[12], R.drawable.d12);
        dIconList.put(weatherType[13], R.drawable.d13);
        dIconList.put(weatherType[14], R.drawable.d14);
        dIconList.put(weatherType[15], R.drawable.d15);
        dIconList.put(weatherType[16], R.drawable.d16);
        dIconList.put(weatherType[17], R.drawable.d17);
        dIconList.put(weatherType[18], R.drawable.d18);
        dIconList.put(weatherType[19], R.drawable.d19);
        dIconList.put(weatherType[20], R.drawable.d20);
        dIconList.put(weatherType[21], R.drawable.d21);
        dIconList.put(weatherType[22], R.drawable.d22);
        dIconList.put(weatherType[23], R.drawable.d23);
        dIconList.put(weatherType[24], R.drawable.d24);
        dIconList.put(weatherType[25], R.drawable.d25);
        dIconList.put(weatherType[26], R.drawable.d26);
        dIconList.put(weatherType[27], R.drawable.d27);
        dIconList.put(weatherType[28], R.drawable.d28);
        dIconList.put(weatherType[29], R.drawable.d29);
        dIconList.put(weatherType[30], R.drawable.d30);
        dIconList.put(weatherType[31], R.drawable.d31);
        dIconList.put("undefined", R.drawable.undefined);
        //加载黑夜的天气图标
        nIconList.put(weatherType[0], R.drawable.n00);
        nIconList.put(weatherType[1], R.drawable.n01);
        nIconList.put(weatherType[2], R.drawable.n02);
        nIconList.put(weatherType[3], R.drawable.n03);
        nIconList.put(weatherType[4], R.drawable.n04);
        nIconList.put(weatherType[5], R.drawable.n05);
        nIconList.put(weatherType[6], R.drawable.n06);
        nIconList.put(weatherType[7], R.drawable.n07);
        nIconList.put(weatherType[8], R.drawable.n08);
        nIconList.put(weatherType[9], R.drawable.n09);
        nIconList.put(weatherType[10], R.drawable.n10);
        nIconList.put(weatherType[11], R.drawable.n11);
        nIconList.put(weatherType[12], R.drawable.n12);
        nIconList.put(weatherType[13], R.drawable.n13);
        nIconList.put(weatherType[14], R.drawable.n14);
        nIconList.put(weatherType[15], R.drawable.n15);
        nIconList.put(weatherType[16], R.drawable.n16);
        nIconList.put(weatherType[17], R.drawable.n17);
        nIconList.put(weatherType[18], R.drawable.n18);
        nIconList.put(weatherType[19], R.drawable.n19);
        nIconList.put(weatherType[20], R.drawable.n20);
        nIconList.put(weatherType[21], R.drawable.n21);
        nIconList.put(weatherType[22], R.drawable.n22);
        nIconList.put(weatherType[23], R.drawable.n23);
        nIconList.put(weatherType[24], R.drawable.n24);
        nIconList.put(weatherType[25], R.drawable.n25);
        nIconList.put(weatherType[26], R.drawable.n26);
        nIconList.put(weatherType[27], R.drawable.n27);
        nIconList.put(weatherType[28], R.drawable.n28);
        nIconList.put(weatherType[29], R.drawable.n29);
        nIconList.put(weatherType[30], R.drawable.n30);
        nIconList.put(weatherType[31], R.drawable.n31);
        nIconList.put("undefined", R.drawable.undefined);
    }

    public static int getDIconId(String weatherType) {
        int iconId = dIconList.get("undefined");
        if (dIconList.get(weatherType) != null) {
            iconId = dIconList.get(weatherType);
        }
        return iconId;
    }

    public static int getNIconId(String weatherType) {
        int iconId = nIconList.get("undefined");
        if (nIconList.get(weatherType) != null) {
            iconId = nIconList.get(weatherType);
        }
        return iconId;
    }
}
