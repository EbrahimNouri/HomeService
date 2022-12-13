package ir.maktab.homeservice.service.expertUser;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.File;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
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
    ExpertRepository expertRepository;

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
    @Autowired
    private BasicServiceRepository basicServiceRepository;
    @Autowired
    private TypeServiceRepository typeServiceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ExpertUserRepository expertUserRepository;

    @BeforeAll
    static void initialize() {
        expert = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = User.builder().firstname("fname").lastname("lname").email("userTest@email.com").password("1234QWear").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 110.0, null, null, basicService);

        order = new Order(typeService, user, null, null, 111.0, "description Test"
                , LocalDate.of(2022, 1, 1), "addrestest", OrderType.DONE);

        expertUser = ExpertUser.builder().expert(expert).user(user).comment("comment").point(3.0).build();
    }
    @BeforeEach
    void setToDb() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
        orderService.OrderRegistration(order);
        expertUser.setOrder(order);
        order.setOrderType(OrderType.DONE);
    }

    @AfterEach
    void purgeDatabase() {
        expertRepository.delete(expert);
        userRepository.delete(user);
        basicServiceRepository.delete(basicService);
        typeServiceRepository.delete(typeService);
        orderRepository.delete(order);
        expertUserRepository.delete(expertUser);
    }

    @AfterAll
    static void purgeOb() {
        expert = null;
        avatar = null;
        user = null;
        typeService = null;
        order = null;
        expertUser = null;
    }


    @Test
    void addCommentAndPoint() {
        service.addCommentAndPoint(expertUser);


        assertAll(
                () -> assertNotNull(service.findById(expertUser))
        );
    }
}