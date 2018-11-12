package com.zhihuishu.springboot.springboothello.rabbitmq.api.consumer;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Consumer;

import java.io.IOException;

public class myConsumer extends DefaultConsumer {

    public myConsumer(Channel channel) {
        super(channel);
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----------------------consumer 开始-----------------------");
        System.out.println("consumerTag :"+consumerTag);
        System.out.println("envelope :"+envelope);
        System.out.println("properties :"+properties);
        System.out.println("body :"+body);
    }
}
