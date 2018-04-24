package com.zhihuishu.springboot.springboothello;

import com.zhihuishu.springboot.springboothello.controller.HelloWorldController;
import com.zhihuishu.springboot.springboothello.dao.UserRepository;
import com.zhihuishu.springboot.springboothello.dto.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import javax.servlet.http.HttpSession;
import java.text.DateFormat;
import java.util.Date;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringbootHelloApplicationTests {

	private MockMvc mvc;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RedisTemplate redisTemplate;

	@Test
	public void test() throws Exception {
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
		String formattedDate = dateFormat.format(date);

		userRepository.save(new User("aa1", "aa@126.com", "aa", "aa123456",formattedDate));
		userRepository.save(new User("bb2", "bb@126.com", "bb", "bb123456",formattedDate));
		userRepository.save(new User("cc3", "cc@126.com", "cc", "cc123456",formattedDate));

		//Assert.assertEquals(9, userRepository.findAll().size());
		//Assert.assertEquals("bb", userRepository.findByUserNameOrEmail("bb", "cc@126.com").getNickName());
		userRepository.delete(userRepository.findByUserName("aa1"));
	}

	@Test
	public void redisTest(){
		//redisTemplate.opsForValue().set("test:set","testValue1");
		Object o = redisTemplate.opsForValue().get("test:set");
		System.out.println(o);
	}

	/*@Test
	public void springSessionTest(HttpSession session){
		session.setAttribute("uid","123456789");
	}*/




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

}
