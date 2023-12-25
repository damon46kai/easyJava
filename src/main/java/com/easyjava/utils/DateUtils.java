package com.easyjava.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {

    public static final String YYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYMMDD = "yyyyMMdd";
    public static final String _YYYMMDD = "yyyy/MM/dd";

    public static String format(Date date, String pattern){
        return new SimpleDateFormat(pattern).format(date);
    }

    public static String parse(String date, String pattern){
        try{
            new SimpleDateFormat(pattern).parse(date);
        }catch (ParseException e){
            e.printStackTrace();
        }
        return null;
    }
}
