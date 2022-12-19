package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.user.UserRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExpertUserServiceImplTest {

    private static Expert expertTest;
    private static File avatar;
    private static User user;
    private static ExpertUser expertUserMain;
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
    UserRepository userRepository;

    @Autowired
    OrderService orderService;

    @Autowired
    TypeServiceService typeServiceService;

    @Autowired
    BasicServicesService basicServicesService;


    @BeforeAll
    static void initialize() {
        expertTest = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").email("testExpertUser@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = User.builder().firstname("fname").lastname("lname").email("userTestExpertUser@email.com").password("1234QWear").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 110.0, null, null, basicService);

        order = new Order(typeService, user, null, null, 111.0, "description Test"
                , LocalDate.of(2022, 1, 1), "addrestest", OrderType.WAITING_FOR_THE_SUGGESTIONS,
                null);

        expertUserMain = ExpertUser.builder().expert(expertTest).user(user).comment("comment").point(3.0).build();
    }

    @BeforeEach
    void setToDb() {
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
        expertService.registerExpert(expertTest, avatar);
        userService.registerUser(user);
        orderService.orderRegistration(order);
        expertUserMain.setOrder(order);
        order.setOrderType(OrderType.PAID);
    }

    @Test
    void addCommentAndPoint() {
        assertAll(
                () -> service.addCommentAndPoint(expertUserMain),

                () -> assertNotNull(service.findById(expertUserMain))
        );
    }
}