package com.example.legendary.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * 计算两个位置的距离
 */
public class MapUtils {

    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    /**
     * 计算两个经纬度之间的距离
     * @param lat1 纬度
     * @param lng1 经度
     * @param lat2 纬度2
     * @param lng2 经度2
     * @return 距离
     */
    public static double getDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));
        double earthRadius = 6371.393;
        s = s * earthRadius;
        s = Math.round(s * 1000);
        return s;
    }

    public static Map<String,Object> findNeighPosition(double longitude, double latitude) {
        //先计算查询点的经纬度范围
        //地球半径千米
        double r = 6371;
        //0.5千米距离
        double dis = 3;
        double dlng = 2 * Math.asin(Math.sin(dis / (2 * r)) / Math.cos(latitude * Math.PI / 180));
        //角度转为弧度
        dlng = dlng * 180 / Math.PI;
        double dlat = dis / r;
        dlat = dlat * 180 / Math.PI;
        double minlat = latitude - dlat;
        double maxlat = latitude + dlat;
        double minlng = longitude - dlng;
        double maxlng = longitude + dlng;
        Map<String,Object> map=new HashMap<>();
        map.put("minlat",minlat);
        map.put("maxlat",maxlat);
        map.put("minlng",minlng);
        map.put("maxlng",maxlng);
        return map;
    }



    }
