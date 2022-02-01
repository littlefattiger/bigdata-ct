package com.atguigu.ct.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
    public static Date parse(String s, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        Date date = null;
        try {
            date = sdf.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public static String format (Date date, String format){
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return  sdf.format(date);
    }
}
