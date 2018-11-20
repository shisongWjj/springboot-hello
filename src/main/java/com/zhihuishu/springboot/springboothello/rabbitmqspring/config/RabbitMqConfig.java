package com.zhihuishu.springboot.springboothello.rabbitmqspring.config;

import ch.qos.logback.core.joran.spi.DefaultClass;
import com.rabbitmq.client.Channel;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.adapter.MessageDelegate;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.converter.ImageMessageConvert;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.converter.PDFMessageConvert;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.converter.TextMessageConverter;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Order;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Package;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.ConsumerTagStrategy;
import org.springframework.amqp.support.converter.ContentTypeDelegatingMessageConverter;
import org.springframework.amqp.support.converter.DefaultClassMapper;
import org.springframework.amqp.support.converter.DefaultJackson2JavaTypeMapper;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Configuration
public class RabbitMqConfig {

    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost("192.168.85.100");
        connectionFactory.setPort(5672);
        connectionFactory.setUsername("guest");
        connectionFactory.setPassword("guest");
        connectionFactory.setVirtualHost("/test001");

        return connectionFactory;
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory){
        RabbitAdmin rabbitAdmin = new RabbitAdmin(connectionFactory);
        rabbitAdmin.setAutoStartup(true);
        return rabbitAdmin;
    }

    /**
     * 针对消费者配置
     * 1.设置交换机类型
     * 2.将队列绑定到交换机
     * fanoutExchange:将消息分发到所有的绑定队列，无routingkey概念
     * headerExchange:通过添加属性key-value匹配
     * directExchange:按照routingkey分发到指定队列
     * topicExchange:关键字匹配
     */
    @Bean
    public TopicExchange exchange001(){
        return new TopicExchange("topic001",true,false);
    }

    @Bean
    public Queue queue001(){
        return new Queue("queue001",true);//队列持久
    }

    @Bean
    public Binding binding001(){
        return BindingBuilder.bind(queue001()).to(exchange001()).with("spring.*");
    }

    @Bean
    public TopicExchange exchange002(){
        return new TopicExchange("topic002",true,false);
    }

    @Bean
    public Queue queue002(){
        return new Queue("queue002",true);//队列持久
    }

    @Bean
    public Binding binding002(){
        return BindingBuilder.bind(queue002()).to(exchange002()).with("rabbit.*");
    }

    @Bean
    public Queue queue003(){
        return new Queue("queue003",true);//队列持久
    }

    @Bean
    public Binding binding003(){
        return BindingBuilder.bind(queue003()).to(exchange001()).with("mq.*");
    }

    @Bean
    public Queue queue_image(){
        return new Queue("queue_image",true);//队列持久
    }

    @Bean
    public Queue queue_pdf(){
        return new Queue("queue_pdf",true);//队列持久
    }

    //模板类
    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        //rabbitTemplate.
        return rabbitTemplate;
    }

    @Bean
    public SimpleMessageListenerContainer messageContainer(ConnectionFactory connectionFactory){
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer(connectionFactory);
        container.setQueues(queue001(),queue002(),queue003(),queue_image(),queue_pdf());//设置监听队列
        container.setConcurrentConsumers(1);//当前的消费数量
        container.setMaxConcurrentConsumers(5);//最大的消费数量
        container.setDefaultRequeueRejected(false);//是否重回队列
        container.setAcknowledgeMode(AcknowledgeMode.AUTO);//手动ack ack:确认字符  签收模式
        container.setConsumerTagStrategy(new ConsumerTagStrategy() {
            @Override
            public String createConsumerTag(String queue) {
                return queue+"-" + UUID.randomUUID();
            }
        });//消费标签策略
        /*
        container.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                String msg = new String(message.getBody());
                System.out.println("-------------消费者："+msg);
            }
        });//监听消息
        */
        /**
         * org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter#defaultListenerMethod
         * 所以这个里的方法 应该要指定方法名称为handleMessage
         * 也可以通过修改这个字段来，修改默认方法的名称
         *
         * 适配器方式，默认是有自己的方法名字的：handlemessage
         * 可以自己指定一个方法的名字：consumermessage
         * 也可以添加一个转换器:从字节数组转换为String
         */
        /*方式一*/
        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumerMessage");
        adapter.setMessageConverter(new TextMessageConverter());
        container.setMessageListener(adapter);*/

        /*方式二*/
        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setMessageConverter(new TextMessageConverter());
        Map<String, String> queueOrTagToMethodName = new HashMap<>();
        queueOrTagToMethodName.put("queue001","method1");
        queueOrTagToMethodName.put("queue002","method2");
        adapter.setQueueOrTagToMethodName(queueOrTagToMethodName);
        container.setMessageListener(adapter);*/

        /*converter*/
        /*1.1 支持json格式的转换器*/
        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();

        adapter.setMessageConverter(jsonMessageConverter);
        container.setMessageListener(adapter);*/

        /*1.2 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象*/

       /* MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        jsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

        //添加信任包，类
        DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        jsonMessageConverter.setClassMapper(classMapper);

        adapter.setMessageConverter(jsonMessageConverter);
        container.setMessageListener(adapter);*/

       /*1.3 DefaultJackson2JavaTypeMapper & Jackson2JsonMessageConverter 支持java对象多映射转换*/
        /*MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumeMessage");
        Jackson2JsonMessageConverter jsonMessageConverter = new Jackson2JsonMessageConverter();
        DefaultJackson2JavaTypeMapper javaTypeMapper = new DefaultJackson2JavaTypeMapper();
        jsonMessageConverter.setJavaTypeMapper(javaTypeMapper);

        Map<String,Class<?>> idClassMapping = new HashMap<>();
        idClassMapping.put("order", Order.class);
        idClassMapping.put("package", Package.class);
        javaTypeMapper.setIdClassMapping(idClassMapping);
        //添加信任包，类
       *//* DefaultClassMapper classMapper = new DefaultClassMapper();
        classMapper.setTrustedPackages("*");
        jsonMessageConverter.setClassMapper(classMapper);*//*

        adapter.setMessageConverter(jsonMessageConverter);
        container.setMessageListener(adapter);*/

        /// 1.4 ext convert
        MessageListenerAdapter adapter = new MessageListenerAdapter(new MessageDelegate());
        adapter.setDefaultListenerMethod("consumMessage");
        ContentTypeDelegatingMessageConverter convert = new ContentTypeDelegatingMessageConverter();

        TextMessageConverter textConvert = new TextMessageConverter();
        convert.addDelegate("text",textConvert);
        convert.addDelegate("html/text",textConvert);
        convert.addDelegate("xml/text",textConvert);
        convert.addDelegate("text/plain",textConvert);

        Jackson2JsonMessageConverter jsonConvert = new Jackson2JsonMessageConverter();
        convert.addDelegate("json",jsonConvert);
        convert.addDelegate("application/json",jsonConvert);

        ImageMessageConvert imageConvert = new ImageMessageConvert();
        convert.addDelegate("image/png",imageConvert);
        convert.addDelegate("image",imageConvert);

        PDFMessageConvert pdfConvert = new PDFMessageConvert();
        convert.addDelegate("application/pdf",pdfConvert);

        adapter.setMessageConverter(convert);
        container.setMessageListener(adapter);

        return container;
    }
}
