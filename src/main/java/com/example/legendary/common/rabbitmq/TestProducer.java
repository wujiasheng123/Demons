package com.example.legendary.common.rabbitmq;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * RabbitMQ fanout模式 消息生成者
 * @Author: 吴嘉晟
 * @Date: 2019/4/1 09:52
 * @Version 1.0
 */
public class TestProducer {
    /**
     * 消费者名称，生产者推送消息到消费者的标识
     */
    private final static String EXCHANGE_NAME="fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
        //判断服务器是否启动
        RabbitMQUtil.checkServer();
        //创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ相关信息
        factory.setHost("127.0.0.1");
        //设置登录账号
        factory.setUsername("admin");
        //设置登录密码
        factory.setPassword("admin");
        //创建一个新的连接
        Connection connection = factory.newConnection();
        //创建一个通道
        Channel channel = connection.createChannel();
        //交换机声明（参数为：交换机名称；交换机类型）
        channel.exchangeDeclare(EXCHANGE_NAME, "fanout");
        
        for (int i = 0; i < 100; i++) {
            String message = "direct 消息 " +i;
            //发送消息到队列中
            channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"));
			System.out.println("发送消息： " + message);
		}
        //关闭通道和连接
        channel.close();
        connection.close();
    }
}