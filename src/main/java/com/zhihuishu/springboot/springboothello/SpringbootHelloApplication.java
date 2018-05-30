package com.zhihuishu.springboot.springboothello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.core.convert.converter.Converter;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import java.util.Locale;

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

	@Bean
	public ViewResolver myViewResolver(){
		return new MyViewResolver();
	}


	public static class MyViewResolver implements ViewResolver{

		@Nullable
		@Override
		public View resolveViewName(String viewName, Locale locale) throws Exception {
			return null;
		}
	}

	@Bean
	public Converter myConverter(){
		return new MyConverter();
	}

	public static class MyConverter implements Converter<String,Boolean>{

		@Nullable
		@Override
		public Boolean convert(String source) {
			return true;
		}
	}
}
