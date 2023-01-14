package ir.maktab.homeservice.service.user;

import ir.maktab.homeservice.entity.base.Person;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
//@EnableConfigurationProperties
class UserServiceImplTest {

    private static Person user;

    @Autowired
    UserService service;
    @BeforeAll
    public static void initialize() {
        user = Person.builder().firstname("testname").lastname("testlname").email("test@mail.com")
                .password("qwER1234").credit(0.0).build();
    }

    @BeforeEach
    void addToDatabase(){
        service.registerUser(user);
    }

    @AfterEach
    public void purgeDatabase(){
        service.delete(user);
        user.setId(null);
    }
    @AfterAll
    static void purgeObj(){
        user = null;
    }

    @Test
    void registerUser() {
        assertNotNull(user.getId());
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        service.changePassword(user, newPass );
        assertEquals(newPass, Objects
                .requireNonNull(service.findById(user.getId())).getPassword());
    }
}