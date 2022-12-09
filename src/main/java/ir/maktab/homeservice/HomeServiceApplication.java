package ir.maktab.homeservice;

import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.service.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class HomeServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HomeServiceApplication.class, args);

//        EntityManager em = x.getBean(EntityManager.class);
//
//        System.out.println(em.isOpen());
/*    var us = run.getBean(UserService.class);
    us.save(new User(null, "ali", "nouri","dasdas@gmail.com","123456aA", null, null,null,null,null,null));*/

    }

}
