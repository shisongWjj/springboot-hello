package com.zhihuishu.springboot.springboothello.conf;

import com.zhihuishu.springboot.springboothello.Filter.MyFilter;
import org.apache.catalina.filters.RemoteIpFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * 自定义Filter
 我们常常在项目中会使用filters用于录调用日志、排除有XSS威胁的字符、执行权限验证等等。Spring Boot自动添加了OrderedCharacterEncodingFilter和HiddenHttpMethodFilter，并且我们可以自定义Filter。
 两个步骤：
 实现Filter接口，实现Filter方法
 添加@Configuration 注解，将自定义Filter加入过滤链
* @Description
* @author shisong
* @date 14:33 2018/4/19
* @modifyNote
* @param
* @return
*/
@Configuration
public class WebConfiguration {

    @Bean
    public RemoteIpFilter remoteIpFilter() {
        return new RemoteIpFilter();
    }

    @Bean
    public FilterRegistrationBean testFilterRegistration() {
        FilterRegistrationBean registration = new FilterRegistrationBean();
        registration.setFilter(new MyFilter());
        registration.addUrlPatterns("/*");//设置需要拦截的url 默认拦截全部
        registration.addInitParameter("paramName", "paramValue");
  /*      registration.addInitParameter("userName", "123");
        registration.addInitParameter("userId", "123123");*/
        registration.setName("MyFilter");//设置filter的名字
        registration.setOrder(1);
        return registration;
    }
}
