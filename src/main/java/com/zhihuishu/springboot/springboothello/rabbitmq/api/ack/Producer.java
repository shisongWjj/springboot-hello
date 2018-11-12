package com.zhihuishu.springboot.springboothello.rabbitmq.api.ack;

import com.rabbitmq.client.*;

import java.util.HashMap;
import java.util.Map;

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

        String exchangeName = "test_ack_exchange";
        String routingKey = "ack.save";


        String msg = "send test ack message";
        for(int i = 0;i<5;i++) {

            Map<String,Object> headers = new HashMap<>();
            headers.put("num",i);

            AMQP.BasicProperties properties = new AMQP.BasicProperties()
                    .builder()
                    .deliveryMode(2)
                    .contentEncoding("UTF-8")
                    .headers(headers)
                    .build();

            channel.basicPublish(exchangeName, routingKey, true, properties, msg.getBytes());
        }

    }
}
