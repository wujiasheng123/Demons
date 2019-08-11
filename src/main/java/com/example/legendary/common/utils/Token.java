package com.example.legendary.common.utils;

import java.util.UUID;

/**
 * 简单token生成
 * @author 吴嘉晟
 * @version 1.1
 */
public class Token {
    public static String token() {
        String[] tokenStr = UUID.randomUUID().toString().split("-");
        StringBuilder token = new StringBuilder();
        for(String str : tokenStr) {
            token.append(str);
        }
        return token.toString();
    }
}
