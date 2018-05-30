package com.zhihuishu.springboot.springboothello.controller;

import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ViewResolver;

import javax.swing.text.View;
import java.util.Arrays;
import java.util.Map;

@Controller
public class HelloController {

    /*@Autowired
    ApplicationContext ico;*/

    @ResponseBody
    @RequestMapping("/hello")
    public String hello(){
        return "hello";
    }

    @RequestMapping("success")
    public String success(Map<String,Object> map){
        map.put("hello","<h1>你好</h1>");
        map.put("users", Arrays.asList("zhangsan","lisi","wangwu"));
        return "success";
    }

    /*@ResponseBody
    @RequestMapping("/getBeanFactory")
    public String getBeanFactory(){
        Map<String, View> stringViewMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(ico, View.class);
        System.out.println(stringViewMap);
        return "success";
    }*/
}
