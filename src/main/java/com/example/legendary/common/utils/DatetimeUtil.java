package com.example.legendary.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Datetime工具类
 * @Author: 吴嘉晟
 * @Date: 2019/3/1 18:39
 * @Version 1.0
 */
public class DatetimeUtil {

    public static String formatDateTime(Date date){
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return sdf.format(date);
    }

    public static String formatDateTime(Date date,String str){
        SimpleDateFormat sdf=new SimpleDateFormat(str);
        return sdf.format(date);
    }

    public static Date formatDateTime(String str,String par){
        try {
            SimpleDateFormat sdf=new SimpleDateFormat(par);
            return sdf.parse(str);
        }catch (ParseException e){
            e.printStackTrace();
            return null;
        }
    }

}
