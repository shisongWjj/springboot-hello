package com.zhihuishu.springboot.springboothello.rabbitmq.api.limit;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public class Consumer {

    public static void main(String[] args) throws Exception{
        //创建连接工厂
        ConnectionFactory connectionFactory = new ConnectionFactory();

        //设置连接参数
        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setVirtualHost("/");
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");

        //创建连接
        Connection connection = connectionFactory.newConnection();

        //创建通道
        Channel channel = connection.createChannel();

        String exchangeName = "test_qos_exchange";
        String exchangeType = "topic";
        String routingKey = "qos.#";
        String queueName = "test_qos_queue";

        //声明队列和交换机，并且绑定
        channel.exchangeDeclare(exchangeName,exchangeType,true);
        channel.queueDeclare(queueName,true,false,false,null);

        channel.queueBind(queueName,exchangeName,routingKey);

        //1.如果要使用限流 autoack 要设置为false
        //2.使用basicQos
        channel.basicQos(0,1,false);
        //自定义消费者
        channel.basicConsume(queueName,false,new myConsumer(channel));
    }

}
