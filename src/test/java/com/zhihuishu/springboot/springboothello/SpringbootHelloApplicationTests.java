package com.zhihuishu.springboot.springboothello;

import com.zhihuishu.springboot.springboothello.test.bean.Person;
import com.zhihuishu.springboot.springboothello.test.controller.HelloWorldController;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
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

import java.sql.Connection;
import java.sql.SQLException;

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

}
