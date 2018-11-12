package com.zhihuishu.springboot.springboothello.rabbitmq.api.message;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * 生产者
 */
public class Procuder {

    public static void main(String[] args) throws Exception{
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

        Map<String,Object> headers = new HashMap<>();
        headers.put("my1","111");
        headers.put("my2","222");

        //附加属性
        AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
                .deliveryMode(2)//是否持久化，2是持久化
                .contentEncoding("UTF-8")//字符集
                .expiration("10000")//过期时间
                .headers(headers)//自定义属性
                .build();

        //4.通过channel发送数据
        for(int i = 0;i<5;i++){
            String msg = "hello rabbitmq";
            //exchange 交换机 当交换机设置为空的时候，默认会走（amqp default）
            //mqp default的规则： The default exchange is implicitly bound to every queue, with a routing key equal to the queue name. It is not possible to explicitly bind to, or unbind from the default exchange. It also cannot be deleted.
            //翻译：默认的交换机会默认的绑定每一个队列（当路由规则的名字等于这个队列的时候）,
            //routingKey 路由规则
            channel.basicPublish("","test002",properties,msg.getBytes());
        }

        //5.关闭连接
        channel.close();
        connection.close();
    }
}
