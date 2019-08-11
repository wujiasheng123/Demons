package com.example.legendary.common.rabbitmq;
import java.io.IOException;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.Consumer;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import cn.hutool.core.util.RandomUtil;

/**
 * RabbitMQ fanout模式 消息消费者
 * @Author: 吴嘉晟
 * @Date: 2019/4/1 09:52
 * @Version 1.0
 */
public class TestCustomer {

    /**
     * 消费者名称，生产者会根据此名称进行消息推送
     */
    private final static String EXCHANGE_NAME="fanout_exchange";

    public static void main(String[] args) throws IOException, TimeoutException {
    	//为当前消费者取随机名
    	final String name = "consumer-"+ RandomUtil.randomString(5);
    	
    	//判断服务器是否启动
    	RabbitMQUtil.checkServer();
        // 创建连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        //设置RabbitMQ地址
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
        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        //获取一个临时队列
        String queueName = channel.queueDeclare().getQueue();
        //队列与交换机绑定（参数为：队列名称；交换机名称；routingKey忽略）
        channel.queueBind(queueName,EXCHANGE_NAME,"");
        
        System.out.println(name +" 等待接受消息");
        //DefaultConsumer类实现了Consumer接口，通过传入一个频道，
        // 告诉服务器我们需要那个频道的消息，如果频道中有消息，就会执行回调函数handleDelivery
        Consumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag, Envelope envelope,
                                       AMQP.BasicProperties properties, byte[] body)
                    throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(name + " 接收到消息 '" + message + "'");
            }
        };
        //自动回复队列应答 -- RabbitMQ中的消息确认机制
        channel.basicConsume(queueName, true, consumer);
    }
}