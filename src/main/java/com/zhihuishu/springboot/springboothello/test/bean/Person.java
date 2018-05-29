package com.zhihuishu.springboot.springboothello.test.bean;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 将配置文件中的每一项 映射到组件中
 * @ConfigurationProperties：告诉springboot将奔雷中的所有属性和配置文件中相关的配置进行绑定；
 * prefix = "person"： 配置文件下哪个下面的所有属性进行--映射
 *
 */
@Component//将person注册到容器中
@ConfigurationProperties(prefix = "person")
//@Validated
@PropertySource("classpath:person.properties")
public class Person {

    //@Value(value = "${person.name}")
    //@Email
    private String name;
    private Integer age;
    private Boolean isMan;
    private Map<String,Object> maps;
    private List<Object> list;
    private Friend friend;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Boolean getMan() {
        return isMan;
    }

    public void setMan(Boolean man) {
        isMan = man;
    }

    public Map<String, Object> getMaps() {
        return maps;
    }

    public void setMaps(Map<String, Object> maps) {
        this.maps = maps;
    }

    public List<Object> getList() {
        return list;
    }

    public void setList(List<Object> list) {
        this.list = list;
    }

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isMan=" + isMan +
                ", maps=" + maps +
                ", list=" + list +
                ", friend=" + friend +
                '}';
    }
}
