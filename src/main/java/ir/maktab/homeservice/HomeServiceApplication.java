package ir.maktab.homeservice;

import ir.maktab.homeservice.Service.UserService;
import ir.maktab.homeservice.entity.User;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@SpringBootApplication
public class HomeServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext x = SpringApplication.run(HomeServiceApplication.class, args);
//        EntityManager em = x.getBean(EntityManager.class);
//
//        System.out.println(em.isOpen());
    var us = x.getBean(UserService.class);
    us.save(new User(null, "ali", "nouti","eem","sasa", Timestamp.valueOf(LocalDateTime.now())));

    }

}
