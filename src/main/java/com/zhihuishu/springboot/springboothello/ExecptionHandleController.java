package com.zhihuishu.springboot.springboothello;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class ExecptionHandleController {

    @ExceptionHandler({ArrayIndexOutOfBoundsException.class})
    @ResponseBody
    public String handleArrayIndexOutOfBoundsException(Exception e){
        e.printStackTrace();
        return "testArrayIndexOutOfBoundsException";
    }


}
