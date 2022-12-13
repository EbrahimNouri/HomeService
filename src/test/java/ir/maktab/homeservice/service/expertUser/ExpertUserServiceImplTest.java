package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.order.OrderRepository;
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
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
@PropertySource("/application.properties")
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
    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    static void initialize() {
        expert = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = User.builder().firstname("fname").lastname("lname").email("userTest@email.com").password("1234QWear").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 0.0, null, null, basicService);

        order = new Order(typeService, user, null, null, 0.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.DONE);

        expertUser = new ExpertUser(expert, user, order, LocalDate.now(), 0.0, "hello comment");
    }

    @BeforeEach
    void setToDb() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
        orderService.OrderRegistration(order);
        order.setOrderType(OrderType.DONE);
        orderRepository.save(order);
        service.addCommentAndPoint(expertUser);
    }


    @Test
    void addCommentAndPoint() {

        assertAll(
                () -> assertNotNull(service.findById(expertUser))
        );
    }
}