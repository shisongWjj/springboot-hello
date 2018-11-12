package com.zhihuishu.springboot.springboothello.rabbitmq.api.exchange.direct;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4DirectExchange {

    public static void main(String[] args) throws Exception {
        //1.创建一个connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //配置链接工厂
        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        //2.通过连接工厂创建一个链接
        Connection connection = connectionFactory.newConnection();

        //3.通过连接创建通道
        Channel channel = connection.createChannel();

        //4.声明交换机
        String exchangeName="test_direct_exchange";
        String routingKey="test.direct";
        String msg = "hello world rabbitmq 4 direct exchange message ...";
        channel.basicPublish(exchangeName,routingKey,null,msg.getBytes());

        channel.close();
        connection.close();

    }

}
