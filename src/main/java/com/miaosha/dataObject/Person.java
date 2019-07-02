package com.miaosha.dataObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

@Component
public class Person {

    @Value("${person.id}")
    private String id;
    private String name;


    public static void main(String[] args) {
        Person person = new Person();
        //System.out.println(person.id+"---"+person.name);//null null
        System.out.println(person.getId() + "---" + person.getName());


    }

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
