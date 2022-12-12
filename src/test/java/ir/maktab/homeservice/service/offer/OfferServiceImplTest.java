package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
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


import java.io.File;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    private static Expert expert;
    private static File avatar;
    private static User user;
    private static ExpertUser expertUser;
    private static Order order;

    private static Offer offer1;
    private static Offer offer2;
    private static Offer offer3;
    private static Offer offer4;

    private static TypeService typeService;

    private static BasicService basicService;
    @Autowired
    ExpertUserService expertUserService;

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
    private OfferService service;

    @BeforeAll
    static void initialize() {
        expert = Expert.builder().firstname("testName").lastname("lname").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user = User.builder().firstname("fname").lastname("lname").email("userTest@email.com").password("1234QWear").build();
        basicService = new BasicService("basicServiceTest", null);

        typeService = new TypeService("subTest", 100.0, null, null, basicService);

        order = new Order(typeService, user, null, null, 101.0, "description Test"
                , null, "addrestest", OrderType.WAITING_EXPERT_SELECTION);

        expertUser = new ExpertUser(expert, user, order, LocalDate.now(), 0.0, "hello comment");

        offer1 = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 2, 1)).suggestedPrice(110.0).expert(expert).order(order).build();
        offer2 = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 3, 1)).suggestedPrice(103.0).expert(expert).order(order).build();
        offer3 = Offer.builder().description("description for offer3").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(104.0).expert(expert).order(order).build();
        offer4 = Offer.builder().description("description for offer4").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(105.0).expert(expert).order(order).build();
    }

    @BeforeEach
    void setToDb() {

    }


    @Test
    void offerRegistrationOrUpdate() {
        expertService.registerExpert(expert, avatar);
        userService.registerUser(user);
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
        expertUserService.addCommentAndPoint(expertUser);
        order.setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
        orderService.OrderRegistration(order);
        assertNotNull(service.findById(1L).orElse(null).getId());
    }

    @Test
    void showOffersByOrder() {
        assertAll(
                () -> assertEquals(order,service.showOffersByOrder(order).get(0))
        );
    }

    @Test
    void chooseOffer() {
    }

    @Test
    void startOfWork() {
    }

    @Test
    void endOfTheWork() {
    }

    @Test
    void findByOrder(){
        service.offerRegistrationOrUpdate(offer1);
        service.offerRegistrationOrUpdate(offer2);
        service.offerRegistrationOrUpdate(offer3);
        service.offerRegistrationOrUpdate(offer4);
        assertNotNull(service.findById(1L));

        assertEquals(4,service.findByOrder(order).size()) ;
//        assertEquals(4,service.findByOrder(order).size()) ;
    }
}