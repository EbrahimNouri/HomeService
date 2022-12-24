package ir.maktab.homeservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HomeServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(HomeServiceApplication.class, args);


//        EntityManager em = x.getBean(EntityManager.class);
//
//        System.out.println(em.isOpen());
/*    var us = run.getBean(UserService.class);
    us.save(new User(null, "ali", "nouri","dasdas@gmail.com","123456aA", null, null,null,null,null,null));*/

    }

}
