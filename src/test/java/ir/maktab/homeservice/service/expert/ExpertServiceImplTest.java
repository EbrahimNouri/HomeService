package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.UnexpectedRollbackException;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertServiceImplTest {
    private static short counter;

    private static Expert[] registerExpert = new Expert[5];
    @Autowired
    private ExpertService service;


    @BeforeAll
    static void initialize() {
        for (int i = 0; i < 5; i++) {
            registerExpert[i] = Expert.builder()
                    .firstname("testName")
                    .lastname("testLastname")
                    .email("tesst" + i + "a" + counter + "@email.com")
                    .password("1234QWer").build();
            counter += 1;
        }
    }

    @BeforeEach
    void setToDatabase() {
        for (int i = 0; i < 5; i++) {
            registerExpert[i].setId(null);
            service.registerExpert(registerExpert[i]
                    , new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg"));
        }
    }

    @AfterEach
    void removeFromDatabase() {
        for (int i = 0; i < 5; i++) {
            service.delete(registerExpert[i].getId());
        }
    }

    @Test
    void registerExpert() {

        assertAll(
                () -> Assertions.assertEquals(registerExpert[0].getEmail(), Objects.requireNonNull
                        (service.findById(registerExpert[0].getId(),
                                Path.of("/Users/ebrahimnouri/IdeaProjects/HomeService/farzad.jpg")))),

                () -> assertTrue(new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg").isFile())
        );
    }

    @Test
    void findById() throws IOException {
        Expert e = service.findById(1L, Path.of("/Users/ebrahimnouri/IdeaProjects/HomeService/farzad.jpg"));
    }

    @Test
    void acceptExpert() {
        assertAll(

                () -> registerExpert[1].setExpertStatus(ExpertStatus.NEW),

                () -> service.acceptExpert(registerExpert[1]),

                () -> assertEquals(ExpertStatus.CONFIRMED, service
                        .findById(registerExpert[1].getId()).getExpertStatus())
        );
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        assertAll(
                () -> service.changePassword(registerExpert[2], newPass),

                () -> assertEquals(newPass, Objects.requireNonNull
                        (service.findById(registerExpert[2].getId()).getPassword())));
    }

    @Test
    void passwordPatternTest() {
        String newPass = "1234QWer";
        assertEquals(newPass, Objects.requireNonNull(service.findById(registerExpert[3]
                .getId())).getPassword());
    }

    @Test
    void emailPatternTest() {
        Expert expert1 = Expert.builder().firstname("testName")
                .lastname("testLastname").email("tesst1emailcom")
                .password("1234QWer").build();
        ;
        assertThrows(ConstraintViolationException.class, () -> service.save(expert1));
    }

    @Test
    void emailUniqueTest() {
        assertThrows(UnexpectedRollbackException.class
                , () -> service.registerExpert
                        (Expert.builder().email("tesst1@email.com")
                                        .password("qwe123ASD").build()
                                , new File("/Users/ebrahimnouri/ss.jpg")));
    }
}