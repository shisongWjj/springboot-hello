package com.zhihuishu.springboot.springboothello;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/exception")
public class HelloController {

    @RequestMapping("/index1")
    public String helloWorld(){
        return "helloWorld";
    }

    /*public static void main(String[] args) {
        SpringApplication.run(HelloController.class, args);
    }*/

    @RequestMapping("/e2/{id}")
    @ResponseBody
    public String testExceptionHandle2(@PathVariable(value = "id") Integer id) {
        List<String> list = Arrays.asList(new String[]{"a","b","c","d"});
        return list.get(id-1);
    }

}
