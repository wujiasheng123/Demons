package com.example.legendary.trunk.users.controller;

import com.example.legendary.config.jwt.LoginToken;
import com.example.legendary.trunk.users.service.UserService;
import com.example.legendary.trunk.users.service.UsersService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 用户控制层
 * @Author: 吴嘉晟
 * @Date: 2019/7/9 11:13
 * @Version 1.0
 */
@RestController
@RequestMapping("/users")
public class UsersController {

    private final
    UsersService usersService;

    private final UserService userService;

    @Autowired
    public UsersController(UsersService usersService,UserService userService) {
        this.usersService = usersService;
        this.userService = userService;
    }

    /**
     * 登录
     * @param account 账号
     * @param password 密码
     * @return 对象数据
     */
    @PostMapping("/login")
    @LoginToken
    @ApiOperation("登陆接口")
    public Object login(String account, String password){
        return usersService.login(account, password);
    }

    @PostMapping("/test")
    @ApiOperation("/测试jpa")
    public Object test(){
        userService.add("rrrr","123456");
        return 1;
    }

}
