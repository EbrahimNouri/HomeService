package ir.maktab.homeservice.service.admin;

import ir.maktab.homeservice.entity.base.Person;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
class AdminServiceImplTest {
    @Autowired
    private AdminService service;
    static Person admin;

    @BeforeAll
    static void initialsAdmin() {
        admin = Person.builder()
                .firstname("admin")
                .lastname("admin")
                .email("admin@email.com")
                .password("1234QWer@")
                .build();
    }


    @Test
    void addAdmin() {
        service.addAdmin(admin);
        assertAll(
                () -> assertNotNull(admin.getId()),

                () -> assertEquals(admin.getId(), service.findById(Objects.requireNonNull(admin).getId()).getId()));
    }


    @Test
    void changePassword() {
        String oldPass = admin.getPassword();

        assertAll(

                () -> service.addAdmin(admin),
                () -> admin.setPassword("#12@qwe"),
                () -> admin.setEmail("email@admin.com"),
                () -> System.out.println(admin.getId()),
                () -> service.changePassword(admin, "!@#qwe12H"),

                () -> assertNotEquals(oldPass, service.findById(admin.getId()).getPassword())
        );

    }
}