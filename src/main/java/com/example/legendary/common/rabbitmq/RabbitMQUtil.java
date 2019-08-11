package com.example.legendary.common.rabbitmq;

import javax.swing.JOptionPane;

import cn.hutool.core.util.NetUtil;

/**
 * RabbitMQ测试
 * @Author: 吴嘉晟
 * @Date: 2019/4/1 09:52
 * @Version 1.0
 */
public class RabbitMQUtil {

	public static void main(String[] args) {
		checkServer();
	}
	static void checkServer() {
		//验证是否开启RabbitMQ服务
		if(NetUtil.isUsableLocalPort(15672)) {
			JOptionPane.showMessageDialog(null, "RabbitMQ 服务器未启动 ");
			System.exit(1);
		}
	}
}
