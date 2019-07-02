package com.miaosha.dataObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Person {

    @Value("${person.id}")

    private String id;
    //如果配置文件里面没有person.name的配置那么他的默认值就是ty
    //如果在配置文件里面是：person.name-->那么打印出来的name值是空，而不是ty
    @Value("${person.name:ty}")
    private String name;



    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
//@Component
