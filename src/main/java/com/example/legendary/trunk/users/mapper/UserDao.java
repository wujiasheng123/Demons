package com.example.legendary.trunk.users.mapper;

import com.example.legendary.trunk.users.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @Author: Simio
 * @Date: 2019/8/6 10:51
 * @Version 1.0
 */
public interface UserDao extends JpaRepository<User,Integer> {
}
