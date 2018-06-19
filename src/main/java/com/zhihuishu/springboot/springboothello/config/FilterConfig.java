package com.zhihuishu.springboot.springboothello.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import java.io.IOException;

@Component
public class FilterConfig {

    @Bean
    public FilterRegistrationBean myFilterRegistrationBean(){
        FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean();
        filterRegistrationBean.setFilter(new MyFilter());
        return filterRegistrationBean;
    }


    public class MyFilter implements Filter{

        @Override
        public void init(javax.servlet.FilterConfig filterConfig) throws ServletException {
            System.out.println("init 开始啦");
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            System.out.println("doFilter 开始啦");
            chain.doFilter(request,response);
        }

        @Override
        public void destroy() {
            System.out.println("doFilter 开始啦");
        }
    }
}
