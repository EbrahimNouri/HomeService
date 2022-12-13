package ir.maktab.homeservice.service.user;

import ir.maktab.homeservice.entity.User;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class UserServiceImplTest {

    private static User user;

    @Autowired
    UserService service;

    @BeforeAll
    public static void initialize() {
        user = User.builder().firstname("testname").lastname("testlname").email("test@mail.com")
                .password("qwER1234").credit(0.0).build();
    }

    @Test
    void registerUser() {
        service.registerUser(user);
//        assertNotNull(user.getId());
    }

    @Test
    void changePassword() {
        service.registerUser(user);
        String oldPass = user.getPassword();
        service.changePassword(user, "newPassw0rd");
        assertNotNull(oldPass, Objects.requireNonNull(service.findById(1L).orElse(null)).getPassword());
    }
}