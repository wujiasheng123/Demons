package com.example.legendary.trunk.users.mapper;

import com.example.legendary.trunk.users.model.SysUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户数据库持久层
 * @Author: 吴嘉晟
 * @Date: 2019/7/9 11:15
 * @Version 1.0
 */
@Mapper
public interface UsersMapper {

    /**
     * 根据用户id获取用户信息
     * @param userId 用户id
     * @return 对象信息
     */
    SysUser selectUserInfoById(@Param("userId")long userId);

    /**
     * 用户使用用户名登录
     * @param account 用户登录账号
     * @return 对象信息
     */
    SysUser userLoginByUserName(@Param("account") String account);

}
