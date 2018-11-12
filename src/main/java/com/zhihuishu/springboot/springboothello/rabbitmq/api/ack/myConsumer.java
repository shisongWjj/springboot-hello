package com.zhihuishu.springboot.springboothello.rabbitmq.api.ack;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

public class myConsumer extends DefaultConsumer {

    private Channel channel;
    public myConsumer(Channel channel) {
        super(channel);
        this.channel = channel;
    }

    @Override
    public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
        System.out.println("-----------------------consumer 开始-----------------------");
        System.out.println("properties :"+properties);
        System.out.println("body :"+body);

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /*if((Integer)properties.getHeaders().get("num") == 0){
            //deliveryTag：DeliveryTag
            //multiple：是否是批量
            //requeue:是否重回队列
            channel.basicNack(envelope.getDeliveryTag(),false,true);
        }else{*/
            channel.basicAck(envelope.getDeliveryTag(),false);
        /*}*/
    }
}
