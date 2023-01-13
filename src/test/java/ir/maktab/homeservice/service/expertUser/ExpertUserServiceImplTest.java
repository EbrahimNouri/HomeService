package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class ExpertUserServiceImplTest {

    private static Person expertTest;
    private static File avatar;
    private static Person user;
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
    OrderService orderService;
    @Autowired
    TypeServiceService typeServiceService;
    @Autowired
    BasicServicesService basicServicesService;
    @Autowired
    private OrderRepository orderRepository;

    @BeforeAll
    static void initialize() {
        expertTest = Person.builder().firstname("testName").lastname("lname").lastname("testLastname").email("testExpertUser@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = Person.builder().firstname("fname").lastname("lname").email("userTestExpertUser@email.com").password("1234QWear").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 110.0
                , null, null, basicService, "description");

        order = new Order(typeService, user, expertUserMain, null, 111.0, "description Test"
                , LocalDate.of(2022, 1, 1), "addrestest", OrderType.WAITING_FOR_THE_SUGGESTIONS,
                null,null);

        expertUserMain = ExpertUser.builder().expert(expertTest).user(user).order(order).comment("commedddnt").point(3.0).build();
    }

    @Test
    void addCommentAndPoint() {

        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
        expertService.registerExpert(expertTest, avatar);
        userService.registerUser(user);
        orderService.orderRegistration(order);
        expertUserMain.setOrder(order);
        order.setOrderType(OrderType.PAID);
        orderRepository.save(order);

        assertAll(
                () -> service.addCommentAndPoint(expertUserMain),

                () -> assertNotNull(service.findByOrderId(order.getId(), user.getId()))

        );
    }
}
