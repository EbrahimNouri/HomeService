package ir.maktab.homeservice.service.transaction;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.transaction.TransactionRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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
    @Autowired
    private ExpertRepository expertRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private TransactionRepository transactionRepository;

    @BeforeAll
    static void initials() {
        user = User.builder().firstname("fname").lastname("lname").email("userTest@email.com").credit(500.0).password("1234QWear").build();

        expert = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").credit(0.0).email("test@email.com")
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
    static void purgeObj(){
        expert = null;
        user = null;
        transaction = null;
    }

    @Test
    void addTransaction() {


        assertAll(
                () -> assertEquals(400.0, userService.findById(user.getId()).orElse(null).getCredit()),
                () -> assertEquals(100.0, expertService.findById(expert.getId()).orElse(null).getCredit()) ,
                () -> assertNotNull(service.findById(transaction))
        );
        transactionRepository.delete(transaction);
    }
}