package com.zhihuishu.springboot.springboothello.rabbitmq.api.confirm;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmListener;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;

public class Producer {

    public static void main(String[] args) throws Exception{
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //设置连接属性
        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/");

        //获取连接
        Connection connection = connectionFactory.newConnection();

        //获取通道
        Channel channel = connection.createChannel();

        //指定消息的确认模式
        channel.confirmSelect();

        //声明 交换机
        String exchangeName = "test_confirm_exchange";
        String routingKey = "confirm.save";

        String msg = "hello world confirm";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        channel.addConfirmListener(new ConfirmListener() {
            @Override
            public void handleAck(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---------------------ack----------------------");
            }

            @Override
            public void handleNack(long deliveryTag, boolean multiple) throws IOException {
                System.out.println("---------------------no ack----------------------");
            }
        });

    }
}
