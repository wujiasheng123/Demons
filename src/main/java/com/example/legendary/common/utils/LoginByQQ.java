package com.example.legendary.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.example.legendary.common.beans.Tencent;
import com.example.legendary.common.beans.UserInfoByTest;

/**
 * 接入第三方QQ授权登录工具类
 * @Author: 吴嘉晟
 * @Date: 2019/8/5 20:23
 * @Version 1.0
 */
public class LoginByQQ {

    /**
     * 调起QQ登录
     * @param token token
     * @param openId QQ用户的唯一标识openID
     * @return 用户信息
     */
    public static String login(String token, String openId){
        //调起QQ登录
        String params = "access_token=" + token + "&oauth_consumer_key=" + Tencent.appId + "&openid=" + openId;
        //获取用户信息
        return HttpUtil.post("https://graph.qq.com/user/get_user_info", params);
    }

    /**
     * 获取用户信息
     * @param userInfoString 用户信息字符串调用上面login获取
     * @param openId openId
     * @return 用户信息对象
     */
    public static UserInfoByTest geyUserInfoByQQ(String userInfoString, String openId){
        //解析json数据
        JSONObject userInfoResult = JSON.parseObject(userInfoString);
        return new UserInfoByTest(userInfoResult.getString("nickname"),userInfoResult.getString("figureurl_qq"),userInfoResult.getString("gender"),openId);
    }

}
