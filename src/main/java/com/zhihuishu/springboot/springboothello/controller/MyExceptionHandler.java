package com.zhihuishu.springboot.springboothello.controller;

import com.zhihuishu.springboot.springboothello.exception.UserNotExistException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class MyExceptionHandler {

    /**
     * 这个方法可以自定义错误页面  但是 返回的数据  不能自适应
    * @Description
    * @author shisong
    * @date 11:30 2018/6/5
    * @modifyNote
    * @param
    * @return
    */
    /*@ResponseBody
    @ExceptionHandler(UserNotExistException.class)
    public Map<String,Object> handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user.notexist");
        map.put("message",e.getMessage());
        return map;
    }*/

    /**
     * 重定向到/error
     * 这个方法可以自适应了 但是返回的都是默认页面， 因为是状态码都是200，由于
     * org.springframework.boot.autoconfigure.web.servlet.error.DefaultErrorViewResolver#resolveErrorView(javax.servlet.http.HttpServletRequest, org.springframework.http.HttpStatus, java.util.Map)
     * 这个方法中存在一个判断 不是4XX,5XX的状态码就 模板类和静态资源下的4xx,5xx页面
     * 所以我们需要手动设置状态码
     * @Description
     * @author shisong
     * @date 11:30 2018/6/5
     * @modifyNote
     * @param
     * @return
     */
    /*@ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e){
        Map<String,Object> map = new HashMap<>();
        map.put("code","user.notexist");
        map.put("message",e.getMessage());
        return "forward:/error";
    }*/

    /**
     * 发现除了原来的属性，其他新增加的属性都没有生效，
     * 分析org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#errorHtml(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)和
     * org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController#error(javax.servlet.http.HttpServletRequest)
     * 发现他们都是从org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController#getErrorAttributes(javax.servlet.http.HttpServletRequest, boolean) 这个方法中获取这些属性的
     * 所以我们需要继承org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
    * @Description
    * @author shisong
    * @date 14:22 2018/6/5
    * @modifyNote
    * @param
    * @return
    */
    /*@ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        //org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml()中的HttpStatus status = getStatus(request);
        //Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        //所以 我们只需要在request中设置javax.servlet.error.status_code  为4xx或者5xx
        Map<String,Object> map = new HashMap<>();
        request.setAttribute("javax.servlet.error.status_code",500);
        map.put("code","user.notexist");
        map.put("message",e.getMessage());
        return "forward:/error";
    }*/

    /**
     * com.zhihuishu.springboot.springboothello.config.MyErrorAttributes 自定义错误信息返回
    * @Description
    * @author shisong
    * @date 17:58 2018/6/5
    * @modifyNote
    * @param
    * @return
    */
    @ExceptionHandler(UserNotExistException.class)
    public String handleException(Exception e, HttpServletRequest request){
        //org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController.errorHtml()中的HttpStatus status = getStatus(request);
        //Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        //所以 我们只需要在request中设置javax.servlet.error.status_code  为4xx或者5xx
        Map<String,Object> map = new HashMap<>();
        request.setAttribute("javax.servlet.error.status_code",500);
        map.put("code","user.notexist");
        map.put("message",e.getMessage());
        request.setAttribute("ext",map);
        return "forward:/error";
    }

}
