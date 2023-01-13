package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/*
@TestConfiguration
*/
@SpringBootTest
class TransactionServiceImplTest {
    static Person expert;
    static Person user;
    static File avatar;
    private static Transaction transaction;
    @Autowired
    private TransactionService service;

    @Autowired
    private ExpertService expertService;
    @Autowired
    private UserService userService;

    @BeforeAll
    static void initials() {
        user = Person.builder().firstname("fname").lastname("lname").email("userTestmail@email.com").credit(500.0).password("1234QWear").build();

        expert = Person.builder().firstname("testName").lastname("lname").lastname("testLastname").credit(0.0).email("testmailTrabaction@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        transaction = new Transaction(expert, user, null, 100.0, TransactionType.TRANSFER);
    }

    @BeforeEach
    void setToDatabase() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);
        service.addTransaction(transaction);
    }


    @AfterAll
    static void purgeObj() {
        expert = null;
        user = null;
        transaction = null;
    }

    @Test
    void addTransaction() {


        assertAll(
                () -> assertEquals(400.0, Objects.requireNonNull
                        (userService.findById(user.getId())).getCredit()),

                () -> assertEquals(100.0, Objects.requireNonNull
                        (expertService.findById(expert.getId())).getCredit()),

                () -> assertNotNull(service.findById(transaction))
        );
    }
}