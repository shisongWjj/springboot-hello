package com.zhihuishu.springboot.springboothello.rabbitmqspring.adapter;

import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Order;

import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Package;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

public class MessageDelegate {

    public void handleMessage(byte[] msg){
        System.out.println("-----------默认方法，消息监听--------"+new String(msg));
    }

    public void consumerMessage(byte[] msg){
        System.out.println("-----------字节数组，消息监听--------"+new String(msg));
    }

    public void consumerMessage(String msg){
        System.out.println("-----------String 类型的，消息监听--------"+msg);
    }

    public void method1(String messagebody){
        System.out.println("-----------method1方法，消息监听--------"+messagebody);
    }

    public void method2(String messagebody){
        System.out.println("-----------method2方法，消息监听--------"+messagebody);
    }

    public void consumeMessage(Map messagebody){
        System.out.println("-----------map方法，消息监听--------"+messagebody);
    }

    public void consumeMessage(Order order){
        System.out.println("id:"+order.getId()+",name:"+order.getName()+",content:"+order.getContent());
    }

    public void consumeMessage(Package pack){
        System.out.println("id:"+pack.getId()+",name:"+pack.getName()+",desc:"+pack.getDesc());
    }

    public void consumMessage(File file){
        System.out.println("文件对象 方法,消息内容:"+file.getName());
    }

}
