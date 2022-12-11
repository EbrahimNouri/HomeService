package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.nio.file.Files;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@PropertySource("applicationTest.properties")
@ExtendWith(MockitoExtension.class)
class ExpertUserServiceImplTest {

    private static Expert expert;
    private static File avatar;
    private static User user;
    private static ExpertUser expertUser;
    private static Order order;

    private static TypeService typeService;

    private static BasicService basicService;
    @Autowired
    ExpertUserService service;

    @Autowired
    ExpertService expertService;

    @Autowired
    UserService userService;

    @Autowired
    OrderService orderService;

    @Autowired
    TypeServiceService typeServiceService;

    @Autowired
    BasicServicesService basicServicesService;

    @BeforeAll
    static void initialize() {
        expert = Expert.builder().firstname("testName").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = User.builder().firstname("nameTest").email("userTest@email.com").password("1234QWer").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 0.0, null, null, basicService);

        order = new Order(typeService, user, null, null, 0.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.WAITING_FOR_THE_SUGGESTIONS);

        expertUser = new ExpertUser(expert, user, order, LocalDate.now(), null, "hello comment");
    }

    @BeforeEach
    void setToDb() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);

    }


    @Test
    void addCommentAndPoint() {
        assertAll(
                () -> service.addCommentAndPoint(expertUser)
//            ()-> assertNotNull(service.findById(expertUser))
        );

    }
}