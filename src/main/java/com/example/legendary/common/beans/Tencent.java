package com.example.legendary.common.beans;

/**
 * 第三方登录所需参数
 * 该工具类使用参数需根据实际开发项目进行更改参数
 * @Author: 吴嘉晟
 * @Date: 2019/8/5 20:30
 * @Version 1.0
 */
public class Tencent {

    /**
     * 微信appId
     */
    public static String appKey = "wxcd67cebf96611b59";

    /**
     * 微信密钥
     */
    public static String appSecret = "fd24628f302f026e4559aa453b4d8739";

    /**
     * 申请Token地址  开发者无需更改
     */
    public static String accessTokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token";

    /**
     * 获取用户信息--QQ  开发者无需更改
     */
    public static String getUserInfoUrl = "https://api.weixin.qq.com/sns/userinfo";

    /**
     * qq的APPID
     */
    public static String appId = "101712657";

}
