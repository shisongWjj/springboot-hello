package com.zhihuishu.springboot.springboothello.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Producer4TopicExchange {

    public static void main(String[] args) throws Exception{
        //1.创建一个connectionFactory
        ConnectionFactory connectionFactory = new ConnectionFactory();
        //配置链接工厂
        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");

        connectionFactory.setAutomaticRecoveryEnabled(true);
        connectionFactory.setNetworkRecoveryInterval(3000);
        //2.通过连接工厂创建一个链接
        Connection connection = connectionFactory.newConnection();

        //3.通过连接创建通道
        Channel channel = connection.createChannel();

        //4.声明交换机
        String exchangeName="test_topic_exchange";
        String routingKey1="user.save";
        String routingKey2="user.update";
        String routingKey3="user.delete.abc";

        //5.发送
        String msg = "hello world rabbitmq 4 topic exchange message ...";
        channel.basicPublish(exchangeName,routingKey1,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKey2,null,msg.getBytes());
        channel.basicPublish(exchangeName,routingKey3,null,msg.getBytes());

        channel.close();
        connection.close();
    }
}
