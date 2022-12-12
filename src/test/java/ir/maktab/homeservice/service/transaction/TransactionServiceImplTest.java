package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

/*
@TestConfiguration
*/
@SpringBootTest
class TransactionServiceImplTest {
    static Expert expert;
    static User user;
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
        user = User.builder().firstname("fname").lastname("lname").email("userTest@email.com").credit(500.0).password("1234QWear").build();

        expert = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").credit(0.0).email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        transaction = new Transaction(expert, user, null, 100.0);
    }

    @BeforeEach
    void setToDatabase() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);
        service.addTransaction(transaction);
    }

    @Test
    void addTransaction() {

        assertAll(
                () -> assertEquals(400.0, userService.findById(user.getId()).orElse(null).getCredit()),
                () -> assertEquals(100.0, expertService.findById(expert.getId()).orElse(null).getCredit()) ,
                () -> assertNotNull(service.findById(transaction))
        );
    }

    @Test
    void chargeAccountBalance() {


    }
}