package com.example.legendary.trunk.users.service;

import com.example.legendary.trunk.users.model.SysUser;

import javax.servlet.http.HttpServletResponse;

/**
 * 用户逻辑接口层
 * @Author: 吴嘉晟
 * @Date: 2019/7/9 11:16
 * @Version 1.0
 */
public interface UsersService {

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 对象信息
     */
    SysUser selectUserInfoById(long userId);

    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @return 对象数据
     */
    Object login(String account, String password);

}
