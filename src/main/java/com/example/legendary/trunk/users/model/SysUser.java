package com.example.legendary.trunk.users.model;

import java.sql.Timestamp;

/**
 * 用户实体类
 * @Author: 吴嘉晟
 * @Date: 2019/7/9 11:22
 * @Version 1.0
 */
public class SysUser {

    private long userId;
    private String account;
    private String userName;
    private String password;
    private String headPortrait;
    private String mobilePhone;
    private String email;
    private java.sql.Timestamp joinTime;
    private java.sql.Timestamp loginTime;
    private String status;

  public SysUser() {
  }

  public long getUserId() {
    return userId;
  }

  public void setUserId(long userId) {
    this.userId = userId;
  }

  public String getAccount() {
    return account;
  }

  public void setAccount(String account) {
    this.account = account;
  }

  public String getUserName() {
    return userName;
  }

  public void setUserName(String userName) {
    this.userName = userName;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public String getHeadPortrait() {
    return headPortrait;
  }

  public void setHeadPortrait(String headPortrait) {
    this.headPortrait = headPortrait;
  }

  public String getMobilePhone() {
    return mobilePhone;
  }

  public void setMobilePhone(String mobilePhone) {
    this.mobilePhone = mobilePhone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public Timestamp getJoinTime() {
    return joinTime;
  }

  public void setJoinTime(Timestamp joinTime) {
    this.joinTime = joinTime;
  }

  public Timestamp getLoginTime() {
    return loginTime;
  }

  public void setLoginTime(Timestamp loginTime) {
    this.loginTime = loginTime;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
}
