package com.example.legendary.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 预先加载数据
 */
@Component
@Order(1)
public class Runner implements CommandLineRunner {


    @Override
    public void run(String... args) throws Exception {
    }
}
