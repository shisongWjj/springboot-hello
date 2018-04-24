package com.zhihuishu.springboot.springboothello.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

/**
 * 四种 从properties 获取参数的方式
 * https://www.cnblogs.com/muliu/p/8252971.html
 */
@Component
@ConfigurationProperties("com.ss")
public class SsProperties {

    private String title;

    private String description;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        try {
            return  new String(description.getBytes("ISO-8859-1"), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
