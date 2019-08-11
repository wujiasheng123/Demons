package com.example.legendary.trunk.users.service.impl;

import com.example.legendary.common.utils.BCrypt;
import com.example.legendary.common.utils.HttpServletUtils;
import com.example.legendary.common.utils.ResultMap;
import com.example.legendary.config.jwt.JwtToken;
import com.example.legendary.config.jwt.JwtUtil;
import com.example.legendary.trunk.users.mapper.UsersMapper;
import com.example.legendary.trunk.users.model.SysUser;
import com.example.legendary.trunk.users.service.UsersService;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 用户逻辑实现层
 * @Author: 吴嘉晟
 * @Date: 2019/7/9 11:16
 * @Version 1.0
 */
@Service
public class UsersServiceImpl implements UsersService {

    private Map<String, Object> resultMap = ResultMap.getResultMap();

    private final UsersMapper usersMapper;

    private final HttpServletUtils httpServletUtils;

    @Autowired
    public UsersServiceImpl(UsersMapper usersMapper, HttpServletUtils httpServletUtils) {
        this.usersMapper = usersMapper;
        this.httpServletUtils = httpServletUtils;
    }

    @Override
    public SysUser selectUserInfoById(long userId) {
        return usersMapper.selectUserInfoById(userId);
    }

    @Override
    public Object login(String account, String password) {
        SysUser sysUser = usersMapper.userLoginByUserName(account);
        if (sysUser == null){
            resultMap.put("code",500);
            resultMap.put("massage","账号不存在！");
            return resultMap;
        } else {
            if (BCrypt.checkpw(password,sysUser.getPassword())){
                //获取token
                String token = JwtUtil.createJWT(sysUser);
                httpServletUtils.setHeader(httpServletUtils.getHttpServletResponse(),token);
                // 提交给realm进行登入，如果错误他会抛出异常并被捕获
                JwtToken jwtToken = new JwtToken(token);
                // 如果没有抛出异常则代表登入成功，返回true
                SecurityUtils.getSubject().login(jwtToken);
//                HttpServletUtils.setHeader(httpServletResponse,token);
                resultMap.put("code",200);
                resultMap.put("token", token);
                return resultMap;
            } else {
                resultMap.put("code",500);
                resultMap.put("massage","密码错误！");
                return resultMap;
            }
        }
    }
}
