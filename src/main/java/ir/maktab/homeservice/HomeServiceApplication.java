package ir.maktab.homeservice;

import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.service.basicServices.BasicServicesServiceImpl;
import ir.maktab.homeservice.service.user.UserService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication
public class HomeServiceApplication {

    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(HomeServiceApplication.class, args);
        var x = run.getBean(BasicServicesServiceImpl.class);
        x.findAll();

//        EntityManager em = x.getBean(EntityManager.class);
//
//        System.out.println(em.isOpen());
/*    var us = run.getBean(UserService.class);
    us.save(new User(null, "ali", "nouri","dasdas@gmail.com","123456aA", null, null,null,null,null,null));*/

    }

}
