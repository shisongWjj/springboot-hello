package com.zhihuishu.springboot.springboothello.rabbitmqspringboot.consumer.consumer;

import com.rabbitmq.client.Channel;
import com.zhihuishu.springboot.springboothello.rabbitmqspringboot.consumer.dto.Order;
import org.springframework.amqp.rabbit.annotation.*;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitReceiver {

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "queue-1",durable = "true"),
            exchange = @Exchange(value = "exchange-1",durable = "true",type = "topic",ignoreDeclarationExceptions = "true"),
            key = "springboot.*"
    ))
    @RabbitHandler
    public void onMessage(Message message, Channel channel)throws Exception{
        System.out.println("---------------------------------------------");
        System.out.println("消费端payload:"+message.getPayload());
        Long deliveryTage = (Long)message.getHeaders().get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTage,false);
    }


    /**
    * spring.rabbitmq.listener.order.queue.name=queue-2
        spring.rabbitmq.listener.order.queue.durable=true
        spring.rabbitmq.listener.order.exchange.name=exchange-1
        spring.rabbitmq.listener.order.exchange.durable=true
        spring.rabbitmq.listener.order.exchange.type=topic
        spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions=true
        spring.rabbitmq.listener.order.key=springboot.*
*/
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "${spring.rabbitmq.listener.order.queue.name}"
                    ,durable = "${spring.rabbitmq.listener.order.queue.durable}"),
            exchange = @Exchange(value = "${spring.rabbitmq.listener.order.exchange.name}"
                    ,durable = "${spring.rabbitmq.listener.order.exchange.durable}"
                    ,type = "${spring.rabbitmq.listener.order.exchange.type}"
                    ,ignoreDeclarationExceptions = "${spring.rabbitmq.listener.order.exchange.ignoreDeclarationExceptions}"),
            key = "${spring.rabbitmq.listener.order.key}"
    ))
    @RabbitHandler
    public void onOrderMessage(@Payload Order order, @Headers Map<String,Object> headers, Channel channel)throws Exception{
        System.out.println("---------------------------------------------");
        System.out.println("消费端payload:"+order.getId());
        Long deliveryTage = (Long)headers.get(AmqpHeaders.DELIVERY_TAG);
        channel.basicAck(deliveryTage,false);
    }

}
