package com.example.legendary.common.beans;

/**
 * 第三方登录用户基本信息
 * @Author: 吴嘉晟
 * @Date: 2019/8/5 20:49
 * @Version 1.0
 */
public class UserInfoByTest {

    /**
     * 用户名
     */
    private String name;

    /**
     * 用户头像
     */
    private String headImg;

    /**
     * 用户性别
     */
    private String sex;

    /**
     * 用户唯一标识
     */
    private String openId;

    public UserInfoByTest(String name, String headImg, String sex, String openId) {
        this.name = name;
        this.headImg = headImg;
        this.sex = sex;
        this.openId = openId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
