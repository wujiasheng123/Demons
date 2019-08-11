package com.example.legendary.common.utils;

import org.apache.commons.collections.map.HashedMap;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * 根据地质获取经纬度
 * @author 吴嘉晟
 * 密钥:f247cdb592eb43ebac6ccd27f796e2d2
 */
public class GetLngAndLat {

    /**
     * 密匙
     */
    private static final String KEY = "f247cdb592eb43ebac6ccd27f796e2d2";

    /**
     * URL
     */
    private static final String URL = "http://api.map.baidu.com/geocoder?address=%s&output=json&key=%s";

    /**
     * @param addr 查询的地址
     * @return map集合 lng 经度 lat 纬度
     */
    public static HashedMap getCoordinate(String addr) throws IOException {
        HashedMap map = new HashedMap();
        //经度
        String lng = null;
        //纬度
        String lat = null;
        String address = null;
        try {
            address = java.net.URLEncoder.encode(addr, "UTF-8");
        }catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        String url = String .format(URL, address,KEY);
        URL myURL = null;
        URLConnection httpsConn;
        try {
            myURL = new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        InputStreamReader insr = null;
        BufferedReader br = null;
        try {
            // 不使用代理
            assert myURL != null;
            httpsConn = myURL.openConnection();
            if (httpsConn != null) {
                insr = new InputStreamReader( httpsConn.getInputStream(), "UTF-8");
                br = new BufferedReader(insr);
                String data;
                int count = 1;
                while((data= br.readLine())!=null){
                    if(count==5){
                        //经度
                        lng = (String)data.subSequence(data.indexOf(":")+1, data.indexOf(","));
                        count++;
                    }else if(count==6){
                        //纬度
                        lat = data.substring(data.indexOf(":")+1);
                        count++;
                    }else{
                        count++;
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(insr!=null){
                insr.close();
            }
            if(br!=null){
                br.close();
            }
        }
        assert lng != null;
        map.put("lng",Double.valueOf(lng));
        assert lat != null;
        map.put("lat",Double.valueOf(lat));
        return map;
    }

//    public static void main(String[] args) throws IOException {
//        Map<String,Double> map = GetLngAndLat.getCoordinate("江西省南昌市青山湖区火炬五路899号");
//        System.out.println(map.get("lng"));//经度
//        System.out.println(map.get("lat"));//纬度
//    }
}
