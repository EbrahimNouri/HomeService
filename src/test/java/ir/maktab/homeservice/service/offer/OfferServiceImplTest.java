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
import java.util.function.BooleanSupplier;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    private static Expert[] expert = new Expert[4];
    private static File[] avatar = new File[4];
    private static User[] user = new User[4];
    private static ExpertUser[] expertUser = new ExpertUser[4];
    private static Order[] order = new Order[4];

    private static Offer[] offer = new Offer[12];

    private static TypeService[] typeService = new TypeService[4];

    private static BasicService[] basicService = new BasicService[4];
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
        expert[0] = Expert.builder().firstname("testName").lastname("testLastname1").email("test1@email.com")
                .password("1234QWer").build();
        avatar[0] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[1] = Expert.builder().firstname("testName").lastname("testLastname2").email("test2@email.com")
                .password("1234QWer").build();
        avatar[1] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[2] = Expert.builder().firstname("testName").lastname("testLastname3").email("test3@email.com")
                .password("1234QWer").build();
        avatar[2] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[3] = Expert.builder().firstname("testName").lastname("testLastname4").email("test4@email.com")
                .password("1234QWer").build();
        avatar[3] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user[0] = User.builder().firstname("fname1").lastname("lname1").email("userTest1@email.com").password("1234QWear").build();
        user[1] = User.builder().firstname("fname2").lastname("lname2").email("userTest2@email.com").password("1234QWear").build();
        user[2] = User.builder().firstname("fname3").lastname("lname3").email("userTest3@email.com").password("1234QWear").build();
        user[3]= User.builder().firstname("fname4").lastname("lname4").email("userTest4@email.com").password("1234QWear").build();

        basicService[0] = new BasicService("basicServiceTest1", null);
        basicService[1] = new BasicService("basicServiceTest2", null);
        basicService[2] = new BasicService("basicServiceTest3", null);
        basicService[3] = new BasicService("basicServiceTest4", null);

        typeService[0] = new TypeService("subTest1", 100.0, null, null, basicService[0]);
        typeService[1] = new TypeService("subTest2", 100.0, null, null, basicService[1]);
        typeService[2] = new TypeService("subTest3", 100.0, null, null, basicService[2]);
        typeService[3] = new TypeService("subTest4", 100.0, null, null, basicService[3]);

        order[0] = new Order(typeService[0], user[0], null, null, 101.0, "description Test"
                , LocalDate.now().plusDays(1), "addrestest", OrderType.WAITING_EXPERT_SELECTION);
        order[1] = new Order(typeService[1], user[1], null, null, 101.0, "description Test"
                , LocalDate.now().plusDays(1), "addrestest", OrderType.WAITING_EXPERT_SELECTION);
        order[2] = new Order(typeService[2], user[2], null, null, 101.0, "description Test"
                , LocalDate.now().plusDays(1), "addrestest", OrderType.WAITING_EXPERT_SELECTION);
        order[3] = new Order(typeService[3], user[3], null, null, 101.0, "description Test"
                , LocalDate.now().plusDays(1), "addrestest", OrderType.WAITING_EXPERT_SELECTION);

        expertUser[0] = new ExpertUser(expert[0], user[0], order[0], LocalDate.now(), 0.0, "hello comment");
        expertUser[1] = new ExpertUser(expert[1], user[1], order[1], LocalDate.now(), 0.0, "hello comment");
        expertUser[2] = new ExpertUser(expert[2], user[2], order[2], LocalDate.now(), 0.0, "hello comment");
        expertUser[3] = new ExpertUser(expert[3], user[3], order[3], LocalDate.now(), 0.0, "hello comment");

        offer[0] = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(110.0).expert(expert[0]).order(order[0]).build();
        offer[1] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[0]).order(order[0]).build();
        offer[2] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[0]).order(order[0]).build();
        offer[3] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[0]).order(order[0]).build();

        offer[4] = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(110.0).expert(expert[1]).order(order[1]).build();
        offer[5] = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(110.0).expert(expert[1]).order(order[1]).build();
        offer[6] = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(110.0).expert(expert[1]).order(order[1]).build();
        offer[7] = Offer.builder().description("description for offer1").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(110.0).expert(expert[1]).order(order[1]).build();

        offer[8] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[2]).order(order[2]).build();
        offer[9] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[2]).order(order[2]).build();
        offer[10] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[2]).order(order[2]).build();
        offer[11] = Offer.builder().description("description for offer2").EndDate(LocalDate.of(2023, 2, 1))
                .startDate(LocalDate.of(2023, 1, 1)).suggestedPrice(103.0).expert(expert[2]).order(order[2]).build();

    }

    @BeforeEach
    void setToDb() {
        for (int i = 0; i < 4; i++) {
            expertService.registerExpert(expert[i], avatar[i]);
            userService.registerUser(user[i]);
            basicServicesService.addBasicService(basicService[i]);
            typeServiceService.addSubService(typeService[i]);
            expertUserService.addCommentAndPoint(expertUser[i]);
            order[i].setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
            orderService.OrderRegistration(order[i]);
        }
        for (int i = 0; i < 12; i++) {
            offer[i].getOrder().setOrderType(OrderType.DONE);
            service.offerRegistrationOrUpdate(offer[i]);
        }
    }


    @Test
    void offerRegistrationOrUpdate() {

    }

    @Test
    void showOffersByOrder() {
        assertTrue(service.showOffersByOrderId(order[0].getId()).stream()
                .anyMatch((offer) -> !offer.getOrder().equals(order[0])));
    }

    @Test
    void chooseOffer() {
//        offer.
    }

    @Test
    void startOfWork() {
    }

    @Test
    void endOfTheWork() {
    }

    @Test
    void findByOrder() {
//        service.offerRegistrationOrUpdate(offer1);
//        service.offerRegistrationOrUpdate(offer2);
//        service.offerRegistrationOrUpdate(offer3);
//        service.offerRegistrationOrUpdate(offer4);
//        assertNotNull(service.findById(1L));
//
//        assertEquals(4, service.findByOrder(order).size());
//        assertEquals(4,service.findByOrder(order).size()) ;
    }
}