package com.example.legendary.common.utils;

import java.util.HashMap;
import java.util.Map;

/**
 * Result 工具类
 * @Author: 吴嘉晟
 * @Date: 2018/3/1 18:39
 * @Version 1.0
 */
public class ResultMap {
    private ResultMap() {

    }

   public static Map<String, Object> getResultMap() {
        return new HashMap<String, Object>();
    }
}
