package com.example.legendary.common.utils;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 懒汉单例缓存
 * @author 吴嘉晟
 * @version1.0
 */
@Component
@EnableAutoConfiguration
public class SingleCacheUtil {

    /**
     * 缓存对象
     */
    private static SingleCacheUtil singleCacheUtil = new SingleCacheUtil();

    /**
     * 缓存
     */
    private static Map<String , Object> cache = new HashMap<>();

    /**
     * 缓存过期时间
     */
    private long timeMillis;

    private SingleCacheUtil() {}

    /**
     * 获取缓存
     * @return 内容
     */
    public static SingleCacheUtil getSingleCacheUtil() {
        return singleCacheUtil;
    }

    /**
     * 设置缓存
     * @param key 键
     * @param value 值
     */
    public void setCache(String key, Object value) {
        cache.put(key,value);
        System.out.println(cache);
    }

    /**
     * 取出缓存
     * @param key 键
     * @return 对应值
     */
    public Object getCache(String key) {
        cleanTimeOutCache();
        return cache.get(key);
    }

    /**
     * 清空缓存
     */
    public void clearCache() {
        cache = new HashMap<>();
    }

    /**
     * 删除指定缓存
     * @param key 键
     */
    public void removeCache(String key) {
        cache.remove(key);
    }

    /**
     * 设置缓存过期时间
     * @param timeMillis 缓存过期时间
     **/
    public void setTimeMillis(long timeMillis){
        this.timeMillis = timeMillis;

    }
    /**
     * 清除过期缓存
     **/
    private void cleanTimeOutCache(){

        Iterator<Map.Entry<String,Object>> it = cache.entrySet().iterator();

        while (it.hasNext()){
            Map.Entry<String,Object> entity = it.next();
            long time = Long.parseLong(entity.getValue().toString().split(":")[1]);
            if(System.currentTimeMillis() - time > timeMillis){
                it.remove();
            }
        }
    }
}