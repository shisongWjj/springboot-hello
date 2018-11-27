package com.zhihuishu.springboot.springboothello.rabbitmqspringcloudstream.consumer.stream;

import com.zhihuishu.springboot.springboothello.rabbitmqspringcloudstream.producer.stream.Barista;
import org.springframework.cloud.stream.annotation.Input;
import org.springframework.cloud.stream.annotation.Output;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.SubscribableChannel;

public interface BaristaV2 {

    String INTPUT_CHANNEL = "input_channel";


    //注解@Input 声明了它是一个输入类型的通道，名字是Barista.INPUT_CHANNEL,也就是position3的input_channel
    //注解@Output 声明了它是一个输出类型的通道，名字是Barista.OUTPUT_CHANNEL,名字是input_channel
    @Input(BaristaV2.INTPUT_CHANNEL)
    SubscribableChannel loginput();

}
