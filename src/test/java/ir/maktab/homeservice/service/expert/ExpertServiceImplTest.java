package ir.maktab.homeservice.service.expert;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
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


@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
@PropertySource("applicationTest.properties")
@ExtendWith(MockitoExtension.class)
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
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
    }

    @Test
    void registerExpert() throws IOException {

        service.registerExpert(expert, avatar
        );
        Files.write(Path.of("/Users/ebrahimnouri/ss.jpg")
                , service.findById(1L).orElseThrow().getAvatar());

        assertAll(
                () -> Assertions.assertEquals(expert.getEmail(), Objects.requireNonNull(service.findById(1L)
                        .orElse(null)).getEmail()),
                () -> assertTrue(new File("/Users/ebrahimnouri/ss.jpg").isFile())
        );
    }

    @Test
    void acceptExpert() {
        expert.setExpertStatus(ExpertStatus.NEW);
        service.registerExpert(expert, avatar);
        expert.setExpertStatus(ExpertStatus.CONFIRMED);
        service.acceptExpert(expert);
        assertEquals(ExpertStatus.CONFIRMED, service.findById(expert.getId()).orElseThrow().getExpertStatus());

    }

    @Test
    void changePassword() {
        service.registerExpert(expert, avatar);
        service.changePassword(expert, "123poIU");
    }

    @Test
    void passwordPatternTest() {
        expert.setPassword("123");
        assertThrows(Exception.class, () -> service.registerExpert(expert, avatar));
    }

    @Test
    void emailPatternTest() {
        expert.setEmail("testemailcom");
        assertThrows(Exception.class, () -> service.registerExpert(expert, avatar));
    }

    @Test
    void emailUniqueTest(){
        service.registerExpert(expert, avatar);
        assertThrows(Exception.class
                ,()-> service.registerExpert
                        (Expert.builder().email("test@email.com").password("qwe123ASD").build(),avatar));
    }
}