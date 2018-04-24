package com.zhihuishu.springboot.springboothello.controller;

import com.zhihuishu.springboot.springboothello.conf.SsProperties;
import com.zhihuishu.springboot.springboothello.dto.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
public class HelloWorldController {

    @Autowired
    private SsProperties ssProperties;

    @RequestMapping("/hello")
    public String index() {
        return "Hello World";
    }

    @RequestMapping("/getUser")
    public User getUser(String userName) {
        System.out.println(ssProperties.getTitle()+":"+ssProperties.getDescription());

        User user=new User();
        user.setUserName("小明");
        user.setPassWord("xxxx");
        return user;
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
