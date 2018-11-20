package com.zhihuishu.springboot.springboothello.rabbitmq.api.dlx;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

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

        // 这就是一个普通的交换机和队列 以及路由
        String exchangeName = "test_dlx_exchange";
        String exchangeType = "topic";
        String routingKey = "dlx.#";
        String queueName = "test_dlx_queue";

        //声明队列和交换机，并且绑定
        channel.exchangeDeclare(exchangeName,exchangeType,true);

        Map<String,Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange","dlx.exchange");

        //这个arguments属性，要设置到声明队列上
        channel.queueDeclare(queueName,true,false,false,arguments);
        channel.queueBind(queueName,exchangeName,routingKey);

        //要进行死信队列的声明：
        channel.exchangeDeclare("dlx.exchange","topic",true,false,null);
        channel.queueDeclare("dlx.queue",true,false,false,null);
        channel.queueBind("dlx.queue","dlx.exchange","#");

        //自定义消费者
        channel.basicConsume(queueName,true,new myConsumer(channel));
    }

}
