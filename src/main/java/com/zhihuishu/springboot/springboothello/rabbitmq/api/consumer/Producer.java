package com.zhihuishu.springboot.springboothello.rabbitmq.api.consumer;

import com.rabbitmq.client.*;

import java.io.IOException;

public class Producer {

    public static void main(String[] args) throws Exception{
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        // 创建连接
        Connection connection = connectionFactory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        String exchangeName = "test_consumer_exchange";
        String routingKey = "consumer.save";


        String msg = "send test consumer message";
        for(int i = 0;i<5;i++) {
            channel.basicPublish(exchangeName, routingKey, true, null, msg.getBytes());
        }

    }
}
