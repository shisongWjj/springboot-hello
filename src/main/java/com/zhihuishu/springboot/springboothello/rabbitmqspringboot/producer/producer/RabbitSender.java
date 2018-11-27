package com.zhihuishu.springboot.springboothello.rabbitmqspringboot.producer.producer;

import com.zhihuishu.springboot.springboothello.rabbitmqspringboot.consumer.dto.Order;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.support.CorrelationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class RabbitSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    final RabbitTemplate.ConfirmCallback confirmCallback = new RabbitTemplate.ConfirmCallback() {
        @Override
        public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            System.out.println("correlationData: "+correlationData);
            System.out.println("ack: "+ack);
            if(!ack){
                System.out.println("异常处理。。。。");
            }else{
                System.out.println("更新数据库。。。。");
            }
        }
    };

    final RabbitTemplate.ReturnCallback returnCallback = new RabbitTemplate.ReturnCallback() {
        @Override
        public void returnedMessage(org.springframework.amqp.core.Message message, int replyCode, String replyText, String exchange, String routingKey) {
            System.out.println("exchange: "+exchange);
            System.out.println("routingKey: "+routingKey);
            System.out.println("replyCode: "+replyCode);
            System.out.println("replyText: "+replyText);
        }
    };


    public void send(Object message, Map<String,Object> headers)throws Exception{
        MessageHeaders mhs = new MessageHeaders(headers);
        Message msg = MessageBuilder.createMessage(message,mhs);
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData cd = new CorrelationData();
        cd.setId("1231231231231");//id+时间戳  全局唯一
        rabbitTemplate.convertAndSend("exchange-1","springboot.hello",msg,cd);
    }

    public void sendOrder(Order order, Map<String,Object> headers)throws Exception{
        rabbitTemplate.setConfirmCallback(confirmCallback);
        rabbitTemplate.setReturnCallback(returnCallback);
        CorrelationData cd = new CorrelationData();
        cd.setId("456456456456456");//id+时间戳  全局唯一
        rabbitTemplate.convertAndSend("exchange-2","springboot.def",order,cd);
    }

}
