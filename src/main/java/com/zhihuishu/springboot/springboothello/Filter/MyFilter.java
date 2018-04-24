package com.zhihuishu.springboot.springboothello.Filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

public class MyFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        Enumeration<String> parameterNames = request.getParameterNames();
            /*FilterRegistrationBean registration = new FilterRegistrationBean(new MyFilter());
            Filter filter = registration.getFilter();
            System.out.println(filter);
            System.out.println(registration.getUrlPatterns());
            System.out.println(registration.getInitParameters());
            System.out.println(registration.getOrder());
            System.out.println(registration.getServletNames());
            System.out.println(registration.getServletRegistrationBeans());*/
        System.out.println("this is MyFilter,url :"+request.getRequestURI());
        filterChain.doFilter(servletRequest, servletResponse);

    }

    @Override
    public void destroy() {

    }
}
