package com.example.legendary.common.utils;

import cn.hutool.http.HttpUtil;
import com.alibaba.fastjson.JSONObject;
import com.example.legendary.common.beans.Tencent;
import com.example.legendary.common.beans.UserInfoByTest;

/**
 * 微信登录--APP授权登录
 * @Author: 吴嘉晟
 * @Date: 2019/8/5 21:02
 * @Version 1.0
 */
public class LoginByWx {

    /**
     * 调起微信授权登录
     * @param code code
     * @return 所需信息集合
     */
    public static String login(String code){
        //调起微信授权
        String params = "appid=" + Tencent.appKey + "&secret=" + Tencent.appSecret + "&code=" + code + "&grant_type=authorization_code";
        //请求微信拿到用户信息
        return HttpUtil.post(Tencent.accessTokenUrl, params);
    }

    /**
     * 获取用户信息
     * @param userInfoString 用户信息字符串调用上面login获取
     * @return 用户信息对象
     */
    public static UserInfoByTest geyUserInfoByQQ(String userInfoString){
        //解析json数据获取token
        String token = JSONObject.parseObject(userInfoString).getString("access_token");
        //构建获取用户信息参数
        String urlInfo = "access_token=" + token +
                "&openid=" + Tencent.appKey +
                "&lang=zh_CN";
        //获取用户信息
        String resultInfo = HttpUtil.post(Tencent.getUserInfoUrl, urlInfo);
        return new UserInfoByTest(JSONObject.parseObject(resultInfo).getString("nickname"),JSONObject.parseObject(resultInfo).getString("headimgurl"),JSONObject.parseObject(resultInfo).getString("sex"),JSONObject.parseObject(resultInfo).getString("openid"));
    }

}
