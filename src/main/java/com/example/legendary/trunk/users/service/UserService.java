package com.example.legendary.trunk.users.service;

import com.example.legendary.trunk.users.model.User;

import java.util.List;

/**
 * @Author: Simio
 * @Date: 2019/8/6 11:01
 * @Version 1.0
 */
public interface UserService {

    void add(String username, String password);

    void deleteByName(String userName);

    List<User> getAllUsers();
}
