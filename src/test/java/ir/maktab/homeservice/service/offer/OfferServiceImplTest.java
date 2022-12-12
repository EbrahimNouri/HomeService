package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    private static Expert[] expert = new Expert[4];
    private static File[] avatar = new File[4];
    private static User[] user = new User[4];
    private static ExpertUser[] expertUser = new ExpertUser[4];
    private static Order[] order = new Order[4];

    private static Offer[] offer = new Offer[4];


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

    @Autowired
    private ExpertTypeServiceService expertTypeServiceService;
    @Autowired
    private ExpertRepository expertRepository;

    @BeforeAll
    static void initialize() {
        expert[0] = Expert.builder().firstname("testName1").lastname("lname1").email("test1@email.com")
                .password("1234QWer").build();
        avatar[0] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[1] = Expert.builder().firstname("testName2").lastname("lname2").email("test2@email.com")
                .password("1234QWer").build();
        avatar[1] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[2] = Expert.builder().firstname("testName3").lastname("lname3").email("test3@email.com")
                .password("1234QWer").build();
        avatar[2] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        expert[3] = Expert.builder().firstname("testName4").lastname("lname4").email("test4@email.com")
                .password("1234QWer").build();
        avatar[3] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        user[0] = User.builder().firstname("fname1").lastname("lname1").email("userTest1@email.com").password("1234QWear").build();
        user[1] = User.builder().firstname("fname2").lastname("lname2").email("userTest2@email.com").password("1234QWear").build();
        user[2] = User.builder().firstname("fname3").lastname("lname3").email("userTest3@email.com").password("1234QWear").build();
        user[3] = User.builder().firstname("fname4").lastname("lname4").email("userTest4@email.com").password("1234QWear").build();
        basicService[0] = new BasicService("basicServiceTest1", null);
        basicService[1] = new BasicService("basicServiceTest2", null);
        basicService[2] = new BasicService("basicServiceTest3", null);
        basicService[3] = new BasicService("basicServiceTest4", null);

        typeService[0] = new TypeService("subTest", 100.0, null, null, basicService[0]);
        typeService[1] = new TypeService("subTest", 100.0, null, null, basicService[1]);
        typeService[2] = new TypeService("subTest", 100.0, null, null, basicService[2]);
        typeService[3] = new TypeService("subTest", 100.0, null, null, basicService[3]);

        order[0] = new Order(typeService[0], user[0], null, null, 120.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.DONE);
        order[1] = new Order(typeService[1], user[1], null, null, 120.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.DONE);
        order[2] = new Order(typeService[2], user[2], null, null, 120.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.DONE);
        order[3] = new Order(typeService[3], user[3], null, null, 120.0, "description Test"
                , LocalDate.now(), "addrestest", OrderType.DONE);

        expertUser[0] = new ExpertUser(expert[0], user[0], order[0], LocalDate.now(), 0.0, "hello comment");
        expertUser[1] = new ExpertUser(expert[1], user[1], order[1], LocalDate.now(), 0.0, "hello comment");
        expertUser[2] = new ExpertUser(expert[2], user[2], order[2], LocalDate.now(), 0.0, "hello comment");
        expertUser[3] = new ExpertUser(expert[3], user[3], order[3], LocalDate.now(), 0.0, "hello comment");

        offer[0] = new Offer(order[1], expert[0], LocalDate.now(), "desss"
                , 130.0, LocalDate.of(2023, 1, 1));
        offer[1] = new Offer(order[1], expert[1], LocalDate.now(), "desss"
                , 112.0, LocalDate.of(2023, 1, 1));
        offer[2]= new Offer(order[2], expert[2], LocalDate.now(), "desss"
                , 118.0, LocalDate.of(2023, 1, 1));
        offer[3] = new Offer(order[3], expert[3], LocalDate.now(), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

    }

    @BeforeEach
    void setToDb() {
        expertService.registerExpert(expert[0], avatar[0]);
        userService.registerUser(user[0]);
        basicServicesService.addBasicService(basicService[0]);
        typeServiceService.addSubService(typeService[0]);
        orderService.OrderRegistration(order[0]);
        orderService.setOrderToDone(order[0]);
        expertUserService.addCommentAndPoint(expertUser[0]);
        List<ExpertTypeService> expertTypeServices = new ArrayList<>();
        expertTypeServices.add(new ExpertTypeService(expert[0], typeService[0]));
        expertTypeServiceService.addExpertToTypeService(expertTypeServices.get(0));
        expert[0].setExpertTypeServices(expertTypeServices);
//        service.offerRegistrationOrUpdate(offer[0]);
//        service.offerRegistrationOrUpdate(offer[1]);
//        service.offerRegistrationOrUpdate(offer[2]);
//        service.offerRegistrationOrUpdate(offer[3]);

    }


    @Test
    void offerRegistrationOrUpdate() {

        assertNotNull(service.findById(1L).orElse(null).getId());
    }

    @Test
    void showOffersByOrder() {
        assertAll(
                () -> assertTrue(
                        () -> service.showOffersByOrder(order[1]).stream()
                                .allMatch((offer) -> offer.getOrder().getId() == 1L),
                        () -> String.valueOf(service.showOffersByOrder(order[0]).size() == 4))
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
    void findByOrder() {
        service.offerRegistrationOrUpdate(offer[0]);
        service.offerRegistrationOrUpdate(offer[1]);
        service.offerRegistrationOrUpdate(offer[2]);
        service.offerRegistrationOrUpdate(offer[3]);
        assertNotNull(service.findById(1L));

        assertEquals(4, service.findByOrder(order[0]).size());
//        assertEquals(4,service.findByOrder(order).size()) ;
    }
}