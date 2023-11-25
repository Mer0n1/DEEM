package com.example.restful.utils;

import java.util.Date;

public class DateUtil {
    private static DateUtil instance;

    private DateUtil() {}

    public static DateUtil getInstance() {
        if (instance == null)
            instance = new DateUtil();
        return instance;
    }

    /** Особый формат для создания UUID: h_m_s d_m_y*/
    public String getDate() {
        Date date = new Date(System.currentTimeMillis());
        String date_str = date.getHours() + "_" + date.getMinutes() + "_" +
                date.getSeconds() + " " + date.getDay() + "_" + date.getMonth() +
                "_" + date.getYear();
        return date_str;
    }

    public String getDateToForm(Date date) {
        String date_str = date.getHours() + "_" + date.getMinutes() + "_" +
                date.getSeconds() + " " + date.getDay() + "_" + date.getMonth() +
                "_" + date.getYear();
        return date_str;
    }
}
