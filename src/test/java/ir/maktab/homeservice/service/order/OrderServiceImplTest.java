package ir.maktab.homeservice.service.order;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.offer.OfferService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    static Expert[] expert = new Expert[4];
    static File[] avatar = new File[4];
    static User[] user = new User[4];
    static ExpertUser[] expertUser = new ExpertUser[4];
    static Order[] order = new Order[4];
    static Offer[][] offer = new Offer[4][3];
    static TypeService[] typeService = new TypeService[4];
    static BasicService[] basicService = new BasicService[4];

    static ExpertTypeService[] expertTypeService = new ExpertTypeService[4];
    @Autowired
    private ExpertTypeServiceService expertTypeServiceService;
    @Autowired
    private UserService userService;
    @Autowired
    private BasicServicesService basicServicesService;
    @Autowired
    private TypeServiceService typeServiceService;
    @Autowired
    private OrderService orderService;
    @Autowired
    private ExpertTypeServiceRepository expertTypeServiceRepository;
    @Autowired
    private OfferService offerService;
    @Autowired
    private ExpertService expertService;

    @BeforeAll
    static void initials() {
        for (int i = 0; i < 4; i++) {

            expert[i] = Expert.builder().firstname("testName4").lastname("lname4").email("testss4" + i + "@email.com")
                    .password("1234QWer").build();
            avatar[i] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

            user[i] = User.builder().firstname("fname4").lastname("lname4")
                    .email("userTesddta4" + i + "@email.com").password("1234QWear").build();

            basicService[i] = new BasicService("basicServiceTsest4" + i, null);

            typeService[i] = new TypeService("subTest4sa" + i, 100.0, null
                    , null, basicService[i], "description");

            expertTypeService[i] = new ExpertTypeService(expert[i], typeService[i]);

            order[i] = new Order(typeService[i], user[i], null, null
                    , 120.0, "description Test" + i
                    , LocalDate.now(), "addrestest", OrderType.WAITING_FOR_THE_SUGGESTIONS, null, null);

            expertUser[i] = new ExpertUser(expert[i], user[i], order[i], null
                    , 4.0, "hello comment4" + i);
            for (int j = 0; j < 3; j++) {
                offer[i][j] = new Offer(order[i], expert[i], LocalDateTime.now().plusDays(1), "desss"
                        , 126.0, LocalDateTime.now().plusDays(5), true);
            }
        }
    }

    @BeforeEach
    public void setToDb() {

        for (int i = 0; i < 4; i++) {
            expertService.registerExpert(expert[i], avatar[i]);
            expert[i].setExpertStatus(ExpertStatus.CONFIRMED);
            userService.registerUser(user[i]);
            basicServicesService.addBasicService(basicService[i]);
            typeServiceService.addSubService(typeService[i]);
            orderService.orderRegistration(order[i]);
            order[i].setOrderType(OrderType.STARTED);
            orderService.setOrderToDone(order[i]);
            expertTypeServiceService.addExpertToTypeService(expertTypeService[i]);
            for (int j = 0; j < 3; j++) {
                offer[i][j].setOrder(order[i]);
                offerService.offerRegistrationOrUpdate(offer[i][j]);
            }
        }
    }

    @Test
    void orderRegistration() {
        Order order1 = new Order(typeService[0], user[0], null, null
                , 111.0, "hello", LocalDate.now(), "address"
                , null, null, null);
        orderService.orderRegistration(order1);

        assertAll(
                () -> assertEquals(order1.getDescription(),
                        Objects.requireNonNull
                                (orderService.findById
                                        (order1.getId()).orElse(null)).getDescription()),

                () -> assertEquals(order1.getOrderType(), OrderType.WAITING_FOR_THE_SUGGESTIONS)
        );
    }

    @Test
    void showOrderSuggestionOrSelection() {
        assertFalse(
                () -> orderService.showOrderSuggestionOrSelection().stream().anyMatch((order) ->
                        order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION)
                                || order.getOrderType().equals(OrderType.DONE)
                                || order.getOrderType().equals(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE)
                                || order.getOrderType().equals(OrderType.PAID)
                                || order.getOrderType().equals(OrderType.STARTED)
                ));
    }

    @Test
    void setOrderToDone() {
        order[2].setOrderType(OrderType.STARTED);
        orderService.setOrderToDone(order[2]);
        assertTrue(() -> order[2].getOrderType().equals(OrderType.DONE));

    }

    @Test
    void startOfWork() {
        order[3].setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
        orderService.save(order[3]);

        Offer offer = new Offer(order[3], expert[3], LocalDateTime.now()/*.plusDays(1)*/, "desss"
                , 126.0, LocalDateTime.now(), true);
        List<Offer> offers = new ArrayList<>(List.of(offer));
        order[3].setOffers(offers);
        orderService.save(order[3]);

        orderService.startOfWork(order[3]);
        assertAll(
                () -> assertEquals(orderService.findById(order[3].getId()).get().getOrderType()
                        , OrderType.STARTED)

        );
//        order[3].setOrderType(OrderType.PAID);
//        assertAll(
//                () -> assertThrows(Exception.class, () -> orderService.endOfTheWork(order[3]))
//        );
    }

    @Test
    void endOfTheWork() {
        order[1].setOrderType(OrderType.STARTED);
        orderService.endOfTheWork(order[1]);
        assertAll(
                () -> assertEquals(orderService.findById(order[1].getId()).get().getOrderType()
                        , OrderType.DONE)
        );

/*        order[1].setOrderType(OrderType.PAID);
        assertAll(
                () -> assertThrows(CustomExceptionUpdate.class, () -> orderService.endOfTheWork(order[1])
                ));*/
    }

    @Test
    void setOrderToPaid() {
    }
}