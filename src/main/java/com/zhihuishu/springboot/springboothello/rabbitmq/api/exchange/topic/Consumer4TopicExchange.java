package com.zhihuishu.springboot.springboothello.rabbitmq.api.exchange.topic;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

public class Consumer4TopicExchange {

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

        //4.声明
        String exchangeName = "test_topic_exchange";
        String exchangeType = "topic";
        String queueName = "test_topic_queue";
        String routingKey = "user.*";
        //String routingKey = "user.#";

        //创建交换机
        channel.exchangeDeclare(exchangeName,exchangeType,true,false,false,null);
        //创建队列
        channel.queueDeclare(queueName,true,false,false,null);
        //建立绑定关系 队列绑定
        channel.queueBind(queueName,exchangeName,routingKey);

        //durable 是否持久化
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);
        channel.basicConsume(queueName,true,queueingConsumer);
        while (true){
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String string = new String(delivery.getBody());
            System.out.println(string);
        }
    }
}
