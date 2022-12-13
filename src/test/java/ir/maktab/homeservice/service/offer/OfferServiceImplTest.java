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

    @Autowired
    private ExpertTypeServiceService expertTypeServiceService;


    @BeforeAll
    static void initialize() {
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
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

        expertUser[3] = new ExpertUser(expert[3], user[3], order[3], null
                , 4.0, "hello comment4");
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
            expertTypeServiceService.addExpertToTypeService(expertTypeService[i]);
            order[i].setOrderType(OrderType.WAITING_EXPERT_SELECTION);
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
        assertEquals( service.findByOrder(offer[1].getOrder()).stream()
                .map((Offer::getSuggestedPrice)).sorted(Comparator.reverseOrder()).toList()
                , service.findByOrderIdSortedPrice(offer[1].getOrder().getId()).stream()
                        .map(Offer::getSuggestedPrice).toList());
    }
}