package com.zhihuishu.springboot.springboothello;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Order;
import com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Package;
import com.zhihuishu.springboot.springboothello.test.bean.Person;
import com.zhihuishu.springboot.springboothello.test.controller.HelloWorldController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.AmqpException;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.sql.DataSource;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootHelloApplicationTests {

	private MockMvc mvc;

	@Autowired
	private ApplicationContext ioc;

	@Autowired
	private Person person;


	/*@Autowired
	private RedisTemplate redisTemplate;*/


	/*@Test
	public void redisTest(){
		//redisTemplate.opsForValue().set("test:set","testValue1");
		Object o = redisTemplate.opsForValue().get("test:set");
		System.out.println(o);
	}*/

	/*@Test
	public void springSessionTest(HttpSession session){
		session.setAttribute("uid","123456789");
	}*/


	@Test
	public void test1(){
		//System.out.println(97 >>> 16);
		String result = "/";
		result = result.substring(0, result.length() - 1);
		System.out.println("result"+result);
	}


	@Before
	public void setUp() throws Exception {
		mvc = MockMvcBuilders.standaloneSetup(new HelloWorldController()).build();
	}

	@Test
	public void getHello() throws Exception {
		ResultActions hello_world = mvc.perform(MockMvcRequestBuilders.get("/hello").accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("Hello World")));
		System.out.println(hello_world);
	}

	@Test
	public void contextLoads() {
	}

	@Test
	public void filterLetter(){
		String s = "sf98 97&^%fdferf";
		s = s.replaceAll("[^0-9]","");
		System.out.println(s);
	}

	@Test
	public void test(){
		boolean b = ioc.containsBean("helloSevice");
		System.out.println(b);
		System.out.println(person);
	}

	@Autowired
	private DataSource dataSource;

	@Test
	public void test2() throws SQLException{
		System.out.println(dataSource.getClass());

		Connection connection = dataSource.getConnection();
		System.out.println(connection);
		connection.close();

	}

	//===================================以下是rabbitmq的测试方法============================================
	@Autowired
	private RabbitAdmin rabbitAdmin;


	@Test
	public void testAdmin(){
		rabbitAdmin.declareExchange(new DirectExchange("test.direct",false,false));

		rabbitAdmin.declareExchange(new TopicExchange("test.topic",false,false));

		rabbitAdmin.declareExchange(new FanoutExchange("test.fanout",false,false));

		rabbitAdmin.declareQueue(new Queue("test.direct.queue",false));

		rabbitAdmin.declareQueue(new Queue("test.topic.queue",false));

		rabbitAdmin.declareQueue(new Queue("test.fanout.queue",false));

		rabbitAdmin.declareBinding(new Binding("test.direct.queue", Binding.DestinationType.QUEUE,"test.direct","direct",new HashMap<>()));

		rabbitAdmin.declareBinding(
				BindingBuilder
				.bind(new Queue("test.topic.queue",false)) //直接创建队列
				.to(new TopicExchange("test.topic",false,false)) //直接创建交换机  建立关联关系
				.with("user.#") //路由key
		);

		rabbitAdmin.declareBinding(
				BindingBuilder.bind(new Queue("test.fanout.queue",false))
				.to(new FanoutExchange("test.fanout",false,false))
		);

		//清空队列数据
		rabbitAdmin.purgeQueue("test.topic.queue",false);
	}

	@Autowired
	private RabbitTemplate rabbitTemplate;

	@Test
	public void testSendMessage(){

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("desc","信息描述。。。");
		messageProperties.getHeaders().put("type","自定义消息类型。。");
		String msg = "hello rabbitmq";
		Message message = new Message(msg.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message, new MessagePostProcessor() {
			@Override
			public Message postProcessMessage(Message message) throws AmqpException {
				System.out.println("---------------添加额外的设置-------------------------");
				message.getMessageProperties().getHeaders().put("rt","这是额外添加的属性");
				return message;
			}
		});

	}

	@Test
	public void testSendMessage2(){

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		String msg = "hello rabbitmq";
		Message message = new Message(msg.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message);
		rabbitTemplate.convertAndSend("topic002", "rabbit.amqp", "hello message");
		rabbitTemplate.convertAndSend("topic001", "mq.amqp", "hello message");

	}

	@Test
	public void testSendMessage3(){

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("text/plain");
		String msg = "hello rabbitmq";
		Message message = new Message(msg.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001", "spring.amqp", message);
		rabbitTemplate.convertAndSend("topic002", "rabbit.amqp", message);
	}

	@Test
	public void testSendJsonMessage() throws Exception {
		Order order = new Order();
		order.setId("001");
		order.setName("订单名称");
		order.setContent("订单消息");

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(order);
		System.out.println(json);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/json");

		Message message = new Message(json.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001","spring.order",message);

	}

	@Test
	public void testSendJavaMessage() throws Exception{
		Order order = new Order();
		order.setId("001");
		order.setName("订单名称");
		order.setContent("订单消息");

		ObjectMapper objectMapper = new ObjectMapper();
		String json = objectMapper.writeValueAsString(order);
		System.out.println("jjjjjjjj:"+json);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("__TypeId__","com.zhihuishu.springboot.springboothello.rabbitmqspring.dto.Order");
		messageProperties.setContentType("application/json");

		Message message = new Message(json.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001","spring.order",message);
	}

	@Test
	public void testSendMappingMessage() throws Exception{
		ObjectMapper objectMapper = new ObjectMapper();

		Order order = new Order();
		order.setId("001");
		order.setName("订单名称");
		order.setContent("订单消息");

		String json1 = objectMapper.writeValueAsString(order);

		MessageProperties messageProperties = new MessageProperties();
		messageProperties.getHeaders().put("__TypeId__","order");
		messageProperties.setContentType("application/json");
		Message message = new Message(json1.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001","spring.order",message);

		Package pack = new Package();
		pack.setId("001");
		pack.setName("包裹名称");
		pack.setDesc("包裹消息");

		String json2 = objectMapper.writeValueAsString(pack);

		MessageProperties messageProperties2 = new MessageProperties();
		messageProperties.getHeaders().put("__TypeId__","package");
		messageProperties.setContentType("application/json");
		Message message2 = new Message(json2.getBytes(),messageProperties);

		rabbitTemplate.convertAndSend("topic001","spring.order",message2);
	}

	@Test
	public void testSendExtConverterMessage() throws Exception{
		/*byte[] bytes = Files.readAllBytes(Paths.get("d:/002_books", "picture.png"));
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("image/png");
		messageProperties.getHeaders().put("extName","png");

		Message message = new Message(bytes,messageProperties);
		rabbitTemplate.send("","queue_image",message);*/

		byte[] bytes = Files.readAllBytes(Paths.get("d:/002_books", "mysql.pdf"));
		MessageProperties messageProperties = new MessageProperties();
		messageProperties.setContentType("application/pdf");

		Message message = new Message(bytes,messageProperties);
		rabbitTemplate.send("","queue_pdf",message);
	}

}
