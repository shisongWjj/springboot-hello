package com.zhihuishu.springboot.springboothello.test.config;

import com.zhihuishu.springboot.springboothello.test.service.HelloSevice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class MyAppConf {

    @Bean
    @Profile("dev")
    public HelloSevice helloSevice(){
        return new HelloSevice();
    }
}
