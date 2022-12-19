package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomPatternInvalidException;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.nio.file.Path;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertServiceImplTest {

    private static Expert[] registerExpert = new Expert[5];
    @Autowired
    private ExpertService service;
    private ExpertRepository repository;


    @BeforeAll
    static void initialize() {
        for (int i = 0; i < 5; i++) {
            registerExpert[i] = Expert.builder().firstname("testName").lastname("testLastname").email("tesst" + i + "@email.com")
                    .password("1234QWer").build();
        }
    }

    @BeforeEach
    void setToDatabase() {
        for (int i = 0; i < 5; i++) {
            service.registerExpert(registerExpert[i]
                    , new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg"));
        }
    }

/*
    @AfterEach
    void removeFromDatabase() {
        for (int i = 0; i < 5; i++) {
            repository.delete(expert[i]);
        }
    }

    @AfterAll
    static void purgeObj(){
        expert = null;
    }
*/

    @Test
    void registerExpert() {

        assertAll(
                () -> Assertions.assertEquals(registerExpert[0].getEmail(), Objects.requireNonNull
                        (service.findById(registerExpert[0].getId(),
                                        Path.of("/Users/ebrahimnouri/IdeaProjects/homeService/temp.jpg"))
                        .orElse(null)).getEmail()),

                // TODO: 12/19/2022 AD find it 
                () -> assertTrue(new File("/Users/ebrahimnouri/IdeaProjects/homeService/temp.jpg").isFile())
        );
    }

    @Test
    void acceptExpert() {
        registerExpert[1].setExpertStatus(ExpertStatus.NEW);
        service.acceptExpert(registerExpert[1]);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(registerExpert[1].getId()).orElseThrow().getExpertStatus());
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        service.changePassword(registerExpert[2], newPass);
        assertEquals(newPass, Objects.requireNonNull(service.findById(registerExpert[2].getId()).orElse(null)).getPassword());
    }

    @Test
    void passwordPatternTest() {
        assertThrows(Exception.class, () -> service.changePassword(registerExpert[3], "123"));
    }

    @Test
    void passwordUnchangedTest() {
        assertThrows(Exception.class, () -> service.changePassword(registerExpert[3], registerExpert[3].getPassword()));
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