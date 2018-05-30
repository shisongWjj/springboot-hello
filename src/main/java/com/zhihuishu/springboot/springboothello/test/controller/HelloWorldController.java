package com.zhihuishu.springboot.springboothello.test.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RequestMapping("/helloWorld/")
@RestController
public class HelloWorldController {

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }


    @RequestMapping("/setSession")
    public String getUuid(HttpSession session){
        String uid = (String)session.getAttribute("uid");
        if(uid != null){
            return uid;
        }
        session.setAttribute("uid","456456456");
        return session.getId();
    }

}
