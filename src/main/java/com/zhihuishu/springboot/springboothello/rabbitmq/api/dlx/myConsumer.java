package com.zhihuishu.springboot.springboothello.rabbitmq.api.dlx;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

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
