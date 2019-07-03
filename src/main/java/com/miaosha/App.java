package com.miaosha;

import com.miaosha.dao.UserDOMapper;
import com.miaosha.dataObject.Car;
import com.miaosha.dataObject.Person;
import com.miaosha.dataObject.UserDO;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Hello world!
 *
 */
//@EnableAutoConfiguration//open auto config
@SpringBootApplication(scanBasePackages = {"com.miaosha"})
@MapperScan( "com.miaosha.dao")
@RestController
public class App implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    private UserDOMapper userDOMapper;

    //使用@Value
    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        //通过spring来获取对象，才能获得配置文件里面的值
        Person person = (Person) event.getApplicationContext().getBean("person");
        person.setId(null);
        System.out.println(person.getId());
        System.out.println(person.getName());
        //---
        Car car = (Car) event.getApplicationContext().getBean("car");
        System.out.println("----");
        car.setId("2s");
        System.out.println(car.getId());
    }

    @RequestMapping("/")
    public String home() {
        UserDO userDO = userDOMapper.selectByPrimaryKey(1);

        if (userDO == null) {
            return "用户对象不存在";
        } else {
            return userDO.getName();
        }
        //return "hello";
    }
    public static void main( String[] args ){

        System.out.println( "Hello World!" );
        SpringApplication.run(App.class,args);
    }
}
