package com.example.legendary.trunk.users.service.impl;

import com.example.legendary.trunk.users.mapper.UserDao;
import com.example.legendary.trunk.users.model.User;
import com.example.legendary.trunk.users.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Author: Simio
 * @Date: 2019/8/6 11:03
 * @Version 1.0
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public void add(String username, String password) {
        User user = new User();
        user.setPassword(password);
        user.setUsername(username);
        userDao.save(user);
    }

    @Override
    public void deleteByName(String userName) {
        User user = new User();
        user.setUsername(userName);
        userDao.delete(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userDao.findAll();
    }
}
