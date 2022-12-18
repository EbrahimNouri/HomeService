package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.repository.user.UserRepository;
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
import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    static Expert[] expert = new Expert[4];
    static File[] avatar = new File[4];
    static User[] user = new User[4];
    static ExpertUser[] expertUser = new ExpertUser[4];
    static Order[] order = new Order[4];
    static Offer[] offer = new Offer[4];
    static TypeService[] typeService = new TypeService[4];
    static BasicService[] basicService = new BasicService[4];

    static ExpertTypeService[] expertTypeService = new ExpertTypeService[4];

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
    private UserRepository userRepository;
    @Autowired
    private BasicServiceRepository basicServiceRepository;
    @Autowired
    private TypeServiceRepository typeServiceRepository;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ExpertTypeServiceRepository expertTypeServiceRepository;
    @Autowired
    private OfferRepository offerRepository;


    @BeforeAll
    static void initialize() {

        for (int i = 0; i < 4; i++) {

            expert[i] = Expert.builder().firstname("testName4").lastname("lname4").email("testss4"+i+"@email.com")
                    .password("1234QWer").build();
            avatar[i] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

            user[i] = User.builder().firstname("fname4").lastname("lname4")
                    .email("userTesddta4"+i+"@email.com").password("1234QWear").build();

            basicService[i] = new BasicService("basicServiceTsest4"+i, null);

            typeService[i] = new TypeService("subTest4sa"+i, 100.0, null
                    , null, basicService[i]);

            expertTypeService[i] = new ExpertTypeService(expert[i], typeService[i]);

            order[i] = new Order(typeService[i], user[i], null, null
                    , 120.0, "description Test"+i
                    , LocalDate.now(), "addrestest", OrderType.DONE, null);

            expertUser[i] = new ExpertUser(expert[i], user[i], order[i], null
                    , 4.0, "hello comment4"+i);

            offer[i] = new Offer(order[i], expert[i], LocalDateTime.now().plusDays(1), "desss"
                    , 126.0, LocalDateTime.now().plusDays(5));
        }
    }

    @BeforeEach
    void setToDb() {

        for (int i = 0; i < 4; i++) {
            expertService.registerExpert(expert[i], avatar[i]);
            expert[i].setExpertStatus(ExpertStatus.CONFIRMED);
            userService.registerUser(user[i]);
            basicServicesService.addBasicService(basicService[i]);
            typeServiceService.addSubService(typeService[i]);
            orderService.orderRegistration(order[i]);
            orderService.setOrderToDone(order[i]);
            expertTypeServiceService.addExpertToTypeService(expertTypeService[i]);
            order[i].setOrderType(OrderType.WAITING_EXPERT_SELECTION);
            service.offerRegistrationOrUpdate(offer[i]);
        }
    }
//    @AfterEach
//    void purgeDatabase() {
//
//        for (int i = 0; i < 4; i++) {
//            expertRepository.delete(expert[i]);
//            userRepository.delete(user[i]);
//            basicServiceRepository.delete(basicService[i]);
//            typeServiceRepository.delete(typeService[i]);
//            orderRepository.delete(order[i]);
//            expertTypeServiceRepository.delete(expertTypeService[i]);
//        }
//        for (int i = 0; i < 12; i++) {
//            offerRepository.delete(offer[i]);
//        }
//    }
//
//    @AfterAll
//    static void purgeObj(){
//        user = null;
//        expert = null;
//        basicService = null;
//        typeService = null;
//        order = null;
//        offer = null;
//    }


    @Test
    void offerRegistrationOrUpdate() {
//        expertService.registerExpert(expert[0], avatar[0]);
//        expert[0].setExpertStatus(ExpertStatus.CONFIRMED);
//        userService.registerUser(user[0]);
//        basicServicesService.addBasicService(basicService[0]);
//        typeServiceService.addSubService(typeService[0]);
//        orderService.orderRegistration(order[0]);
//        orderService.setOrderToDone(order[0]);
//        expertTypeServiceService.addExpertToTypeService(expertTypeService[0]);
//        order[0].setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
//        service.offerRegistrationOrUpdate(offer[0]);

        assertEquals(offer[0], Objects.requireNonNull(service.findById(offer[0].getId()).orElse(null)));
    }

    @Test
    void showOffersByOrder() {
        assertAll(
                () -> assertTrue(

                        () -> service.showOffersByOrder(order[0]).stream()
                                .map((offer) -> offer.getOrder().getId()).toList().size() == 1,

                        () -> String.valueOf(service.showOffersByOrder(order[0]).size() == 1))

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

        assertEquals(1, service.findByOrder(order[0]).size());
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