package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.offer.OfferRepository;
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.io.File;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    private static Expert[] expert = new Expert[4];
    private static File[] avatar = new File[4];
    private static User[] user = new User[4];
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
        user[0] = User.builder().firstname("fname").lastname("lname").email("userTest1@email.com").password("1234QWear").build();
        user[1] = User.builder().firstname("fname").lastname("lname").email("userTest2@email.com").password("1234QWear").build();
        user[2] = User.builder().firstname("fname").lastname("lname").email("userTest3@email.com").password("1234QWear").build();
        user[3] = User.builder().firstname("fname").lastname("lname").email("userTest4@email.com").password("1234QWear").build();

        basicService[0] = new BasicService("basicServiceTest1", null);
        basicService[1] = new BasicService("basicServiceTest2", null);
        basicService[2] = new BasicService("basicServiceTest3", null);
        basicService[3] = new BasicService("basicServiceTest4", null);

        typeService[0] = new TypeService("subTest1", 100.0, null, null, basicService[0]);
        typeService[1] = new TypeService("subTest2", 100.0, null, null, basicService[1]);
        typeService[2] = new TypeService("subTest3", 100.0, null, null, basicService[2]);
        typeService[3] = new TypeService("subTest4", 100.0, null, null, basicService[3]);

        order[0] = new Order(typeService[0], user[0], null, null, 110.0, "description Test", LocalDate.now(), "addrestest", OrderType.DONE);
        order[1] = new Order(typeService[1], user[1], null, null, 110.0, "description Test", LocalDate.now(), "addrestest", OrderType.DONE);
        order[2] = new Order(typeService[2], user[2], null, null, 110.0, "description Test", LocalDate.now(), "addrestest", OrderType.DONE);
        order[3] = new Order(typeService[3], user[3], null, null, 110.0, "description Test", LocalDate.now(), "addrestest", OrderType.DONE);

        /*--------------------------------EXPERT-------------------------------------*/
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
        /*------------------------------OFFER-------------------------------------*/
        offer[0] = new Offer(order[0], expert[0], LocalDate.now().plusDays(1), "desss"
                , 130.0, LocalDate.of(2023, 1, 1));

        offer[1] = new Offer(order[0], expert[0], LocalDate.now().plusDays(1), "desss"
                , 112.0, LocalDate.of(2023, 1, 1));

        offer[2] = new Offer(order[0], expert[0], LocalDate.now().plusDays(1), "desss"
                , 118.0, LocalDate.of(2023, 1, 1));

        offer[3] = new Offer(order[0], expert[0], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[4] = new Offer(order[1], expert[1], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[5] = new Offer(order[1], expert[1], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[6] = new Offer(order[1], expert[1], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[7] = new Offer(order[1], expert[1], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[8] = new Offer(order[2], expert[2], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[9] = new Offer(order[2], expert[2], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[10] = new Offer(order[2], expert[2], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));

        offer[11] = new Offer(order[2], expert[2], LocalDate.now().plusDays(1), "desss"
                , 126.0, LocalDate.of(2023, 1, 1));
    }

    @BeforeEach
    void setToDb() {

        for (int i = 0; i < 4; i++) {

            expertService.registerExpert(expert[i], avatar[i]);
            userService.registerUser(user[i]);
            basicServicesService.addBasicService(basicService[i]);
            typeServiceService.addSubService(typeService[i]);
            orderService.OrderRegistration(order[i]);
            orderService.setOrderToDone(order[i]);
            order[i].setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);

        }
        for (int i = 0; i < 12; i++) {
            service.offerRegistrationOrUpdate(offer[i]);
            service.offerRegistrationOrUpdate(offer[i]);
            service.offerRegistrationOrUpdate(offer[i]);
            service.offerRegistrationOrUpdate(offer[i]);
        }
    }


    @Test
    void offerRegistrationOrUpdate() {
        assertEquals(offer[0], Objects.requireNonNull(service.findById(1L).orElse(null)));
    }

    @Test
    void showOffersByOrder() {
        assertAll(
                () -> assertTrue(
                        () -> service.showOffersByOrder(order[0]).stream()
                                .map((offer) -> offer.getOrder().getId()).toList().size() == 4,

                        () -> String.valueOf(service.showOffersByOrder(order[0]).size() == 3))
        );
    }

    @Test
    void chooseOffer() {
        offer[0].getOrder().setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
        service.chooseOffer(offer[0]);
        assertAll(
                () -> assertEquals(Objects.requireNonNull(service.findById(offer[0].getId())
                                        .orElse(null)).getOrder()
                                .getOrderType(), OrderType.WAITING_FOR_COME_TO_YOUR_PLACE
                        /*|| OrderType.WAITING_FOR_THE_SUGGESTIONS*/)
        );
    }

    @Test
    void startOfWork() {
        offer[0].getOrder().setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
        service.startOfWork(offer[0]);
        assertAll(
                () -> assertEquals(Objects.requireNonNull(service.findById(offer[0].getId())
                                .orElse(null)).getOrder()
                        .getOrderType(), OrderType.STARTED)
        );
    }

    @Test
    void endOfTheWork() {
        offer[0].getOrder().setOrderType(OrderType.STARTED);
        service.endOfTheWork(offer[0]);
        assertAll(
                () -> assertEquals(Objects.requireNonNull(service.findById(offer[0].getId())
                                .orElse(null)).getOrder()
                        .getOrderType(), OrderType.DONE)
        );
    }

    @Test
    void findByOrder() {

        assertNotNull(service.findById(1L));

        assertEquals(4, service.findByOrder(order[0]).size());
//        assertEquals(4,service.findByOrder(order).size()) ;
    }

    @Test
    void findByOrderIdSortedPrice() {
        assertNotNull(service.findById(1L));
        assertEquals(service.findByOrder(offer[1].getOrder()).stream()
                        .map((Offer::getSuggestedPrice)).sorted(Comparator.reverseOrder()).toList()
                , service.findByOrderIdSortedPrice(offer[1].getOrder().getId()).stream()
                        .map(Offer::getSuggestedPrice).toList());
    }
}