package com.zhihuishu.springboot.springboothello.rabbitmq.api.returnlistener;

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

        String exchangeName = "test_return_exchange";
        String routingKey = "return.save";
        String routingKeyErr = "abc.save";

        channel.addReturnListener(new ReturnListener() {
            @Override
            public void handleReturn(int replyCode, String replyText, String exchange, String routingKey, AMQP.BasicProperties properties, byte[] body) throws IOException {
                System.out.println("replyCode" + replyCode);
                System.out.println("replyText" + replyText);
                System.out.println("exchange" + exchange);
                System.out.println("routingKey" + routingKey);
                System.out.println("properties" + properties);
                System.out.println("body" + body);
            }
        });

        String msg = "send test return message";
        //channel.basicPublish(exchangeName,routingKey,true,null,msg.getBytes());

        channel.basicPublish(exchangeName,routingKeyErr,true,null,msg.getBytes());
    }
}
