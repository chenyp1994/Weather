package com.chenyp.jpzjweather.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Created by chenyp on 15-7-2.
 */
public class DateUtil {

    /**
     * 获得指定日期的前一天
     *
     * @param specifiedDay
     * @param before
     * @param format
     * @return
     * @throws Exception
     */
    public static String getSpecifiedDayBefore(String specifiedDay, int before,
                                               String format) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format, Locale.CHINA)
                    .parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day - before);

        String dayBefore = new SimpleDateFormat(format, Locale.CHINA).format(c
                .getTime());
        return dayBefore;
    }

    /**
     * 获得指定日期的后n天
     *
     * @param specifiedDay
     * @param after
     * @param format
     * @return
     */
    public static String getSpecifiedDayAfter(String specifiedDay, int after,
                                              String format) {
        Calendar c = Calendar.getInstance();
        Date date = null;
        try {
            date = new SimpleDateFormat(format, Locale.CHINA)
                    .parse(specifiedDay);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        c.setTime(date);
        int day = c.get(Calendar.DATE);
        c.set(Calendar.DATE, day + after);

        return new SimpleDateFormat(format, Locale.CHINA).format(c.getTime());
    }

    public static String getCurDate(String format) {
        return new SimpleDateFormat(format, Locale.CHINA).format(new Date());
    }

}
