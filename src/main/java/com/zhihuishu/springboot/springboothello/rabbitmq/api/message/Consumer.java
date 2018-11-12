package com.zhihuishu.springboot.springboothello.rabbitmq.api.message;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.QueueingConsumer;

import java.util.Map;

/**
 * 消费者
 */
public class Consumer {

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

        //4.声明一个队列
        String queueName = "test002";
        channel.queueDeclare(queueName,true,false,false,null);

        //5,创建消费者
        QueueingConsumer queueingConsumer = new QueueingConsumer(channel);

        //6设置channel
        channel.basicConsume(queueName,true,queueingConsumer);

        while (true){
            //7获取消息
            QueueingConsumer.Delivery delivery = queueingConsumer.nextDelivery();
            String msg = new String(delivery.getBody());
            System.out.println(msg);
            Map<String, Object> headers = delivery.getProperties().getHeaders();
            System.out.println(headers);
            //Envelope envelope = delivery.getEnvelope();
        }

    }
}
