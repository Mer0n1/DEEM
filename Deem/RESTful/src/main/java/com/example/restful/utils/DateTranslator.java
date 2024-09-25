package com.example.restful.utils;

import java.util.Calendar;
import java.util.Date;

public class DateTranslator {

    private static DateTranslator dateTranslator;
    private String[] days;
    private String[] date;

    private DateTranslator() {
        days = new String[] {"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        date = new String[] {"января", "февраля", "марта", "апреля", "мая", "июня",
                "июля", "августа", "сентября", "октября", "ноября", "декабря"};
    }

    public static DateTranslator getInstance() {
        if (dateTranslator == null)
            dateTranslator = new DateTranslator();
        return dateTranslator;
    }

    public String toString(Date date) {
        String result = new String();
        result += date.getDate() + "." + (date.getMonth()+1) + "." +
                (1900 + date.getYear()) + "  " + date.getHours() + ":" + date.getMinutes();
        return String.format("%02d.%02d", date.getDate(), date.getMonth() + 1) + "." + (1900 + date.getYear()) + " " + TimeToString(date);
    }

    public String IntToStringDay(int day) {
        if (day >= 0 && day <= 6)
            return days[day];
        else
            return "";
    }

    public String IntToStringMonth(int month) {
        if (month >= 0 && month <= 11)
            return date[month];
        else
            return "";
    }

    public String TimeToString(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int hours = calendar.get(Calendar.HOUR_OF_DAY);
        int minutes = calendar.get(Calendar.MINUTE);

        return String.format("%02d:%02d", hours, minutes);
    }

    public String DayMonthToString(Date date) {
        return date.getDate() + " " + IntToStringMonth(date.getMonth());
    }
}
