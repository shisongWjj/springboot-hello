package com.zhihuishu.springboot.springboothello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 *http://www.ityouknow.com/spring-boot.html(参照)
 */
//@ImportResource(locations = {"classpath:beans.xml"})
//@ImportResource("classpath:beans.xml")
@SpringBootApplication
public class SpringbootHelloApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringbootHelloApplication.class, args);
	}
}
