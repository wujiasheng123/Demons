package com.example.legendary.trunk.users.model;

import javax.persistence.*;

/**
 * @Author: Simio
 * @Date: 2019/8/6 10:45
 * @Version 1.0
 */

/**
 * //告诉JPA这是一个实体类（和数据表映射的类）
 */
@Entity

/**
 * //@Table来指定和哪个数据表对应;如果省略默认表名就是user；
 */
@Table(name = "user")
public class User {

    /**
     * 这是一个主键
     * 自增主键
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 这是和数据表对应的一个列
     */
    @Column(name = "username",length = 50)
    private String username;

    /**
     * 这是和数据表对应的一个列
     */
    @Column(name = "password",length = 50)
    private String password;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
