package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.exception.CustomPatternInvalidException;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicReference;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertServiceImplTest {

    private static Expert expert;
    private static File avatar;
    @Autowired
    private ExpertService service;
    @Autowired
    private ExpertRepository repository;
    @Autowired
    private ExpertTypeServiceRepository expertTypeServiceRepository;

    @BeforeAll
    static void initialize() {
        expert = Expert.builder().firstname("testName").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
    }

    @BeforeEach
    void setToDatabase(){
        expert.setId(null);
        service.registerExpert(expert, null);
    }
    @AfterEach
    void removeFromDatabase(){
        repository.delete(expert);
    }

    @Test
    void registerExpert() {

        assertAll(
                () -> Assertions.assertEquals(expert.getEmail(), Objects.requireNonNull(service.findById(expert.getId())
                        .orElse(null)).getEmail()),
                () -> assertTrue(new File("/Users/ebrahimnouri/ss.jpg").isFile())
        );
    }

    @Test
    void acceptExpert() {
        expert.setExpertStatus(ExpertStatus.NEW);
        service.acceptExpert(expert);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(expert.getId()).orElseThrow().getExpertStatus());
    }

    @Test
    void changePassword() {
        String newPass = "newPassw0rd";
        service.changePassword(expert, newPass );
        assertEquals(newPass, Objects.requireNonNull(service.findById(expert.getId()).orElse(null)).getPassword());
    }

    @Test
    void passwordPatternTest() {
        assertThrows(Exception.class, () -> service.changePassword(expert, "123"));
    }

    @Test
    void emailPatternTest() {
        Expert expert1 = Expert.builder().firstname("testName")
                .lastname("testLastname").email("testemailcom")
                .password("1234QWer").build();

        assertThrows(CustomPatternInvalidException.class
                , () ->service.registerExpert(expert1, null) );
    }

    @Test
    void emailUniqueTest(){
        assertThrows(Exception.class
                ,()-> service.registerExpert
                        (Expert.builder().email("test@email.com").password("qwe123ASD").build(),avatar));
    }
}