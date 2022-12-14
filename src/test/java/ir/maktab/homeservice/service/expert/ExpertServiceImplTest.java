package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomPatternInvalidException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertServiceImplTest {

    private static Expert[] expert = new Expert[5];
    @Autowired
    private ExpertService service;


    @BeforeAll
    static void initialize() {
        for (int i = 0; i < 5; i++) {
            expert[i] = Expert.builder().firstname("testName").lastname("testLastname").email("test" + i + "@email.com")
                    .password("1234QWer").build();
        }
    }

    @BeforeEach
    void setToDatabase() {
        for (int i = 0; i < 5; i++) {
            service.registerExpert(expert[i], new File("/Users/ebrahimnouri/ss.jpg"));
        }
    }

//    @AfterEach
//    void removeFromDatabase() {
//        for (int i = 0; i < 5; i++) {
//            repository.delete(expert[i]);
//        }
//    }

    @AfterAll
    static void purgeObj(){
        expert = null;
    }

    @Test
    void registerExpert() {

        assertAll(
                () -> Assertions.assertEquals(expert[0].getEmail(), Objects.requireNonNull(service.findById(expert[0].getId())
                        .orElse(null)).getEmail()),

                () -> assertTrue(new File("/Users/ebrahimnouri/ss.jpg").isFile())
        );
    }

    @Test
    void acceptExpert() {
        expert[1].setExpertStatus(ExpertStatus.NEW);
        service.acceptExpert(expert[1]);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(expert[1].getId()).orElseThrow().getExpertStatus());
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        service.changePassword(expert[2], newPass);
        assertEquals(newPass, Objects.requireNonNull(service.findById(expert[2].getId()).orElse(null)).getPassword());
    }

    @Test
    void passwordPatternTest() {
        assertThrows(Exception.class, () -> service.changePassword(expert[3], "123"));
    }

    @Test
    void emailPatternTest() {
        Expert expert1 = Expert.builder().firstname("testName")
                .lastname("testLastname").email("testemailcom")
                .password("1234QWer").build();

        assertThrows(CustomPatternInvalidException.class
                , () -> service.registerExpert(expert1, null));
    }

    @Test
    void emailUniqueTest() {
        assertThrows(Exception.class
                , () -> service.registerExpert
                        (Expert.builder().email("test@email.com").password("qwe123ASD").build(),new File("/Users/ebrahimnouri/ss.jpg")));
    }
}