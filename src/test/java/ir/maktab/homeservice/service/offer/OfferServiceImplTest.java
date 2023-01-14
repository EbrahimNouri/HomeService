package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class OfferServiceImplTest {


    static Person[] expert = new Person[4];
    static File[] avatar = new File[4];
    static Person[] user = new Person[4];
    static ExpertUser[] expertUser = new ExpertUser[4];
    static Order[] order = new Order[4];
    static Offer[][] offerTest = new Offer[4][3];
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



    @BeforeAll
    static void initialize() {

        for (int i = 0; i < 4; i++) {

            expert[i] = Person.builder().firstname("testName4").lastname("lname4").email("testss4" + i + "@email.com")
                    .password("1234QWer").build();
            avatar[i] = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");

            user[i] = Person.builder().firstname("fname4").lastname("lname4")
                    .email("userTesddta4" + i + "@email.com").password("1234QWear").build();

            basicService[i] = new BasicService(null,"basicServiceTsest4" + i, null);

            typeService[i] = new TypeService("subTest4sa" + i, 100.0+(i*2), null
                    , null, basicService[i], "description");

            expertTypeService[i] = new ExpertTypeService(expert[i], typeService[i]);

            order[i] = new Order(typeService[i], user[i], null, null
                    , 120.0+i, "description Test" + i
                    , LocalDate.now(), "addrestest", OrderType.DONE, null,null);

            expertUser[i] = new ExpertUser(expert[i], user[i], order[i], null
                    , 4.0, "hello comment4" + i);
            for (int j = 0; j < 3; j++) {
                offerTest[i][j] = new Offer(order[i], expert[i], LocalDateTime.now().plusDays(1), "desss"
                        , 126.0, LocalDateTime.now().plusDays(5), false);
            }
        }
    }

    @BeforeEach
    public void setToDb() {

        for (int i = 0; i < 4; i++) {
            expert[i].setAverageScore((double) (i+1));
            expertService.registerExpert(expert[i], avatar[i]);
            expert[i].setExpertStatus(ExpertStatus.CONFIRMED);
            userService.registerUser(user[i]);
            basicServicesService.addBasicService(basicService[i]);
            typeServiceService.addSubService(typeService[i]);
            orderService.orderRegistration(order[i]);
            order[i].setOrderType(OrderType.STARTED);
            orderService.setOrderToDone(order[i]);
            expertTypeServiceService.addExpertToTypeService(expertTypeService[i]);
            order[i].setOrderType(OrderType.WAITING_EXPERT_SELECTION);
            for (int j = 0; j < 3; j++) {
                service.offerRegistrationOrUpdate(offerTest[i][j]);
            }
        }
    }

    @AfterAll
    static void purgeObj() {
        user = null;
        expert = null;
        basicService = null;
        typeService = null;
        order = null;
        offerTest = null;
    }


    @Test
    void offerRegistrationOrUpdate() {

        assertEquals(offerTest[0][0], Objects.requireNonNull(service.findById(offerTest[0][0].getId())));
    }

    @Test
    void showOffersByOrder() {
        assertAll(
                () -> assertTrue(

                        () -> service.showOffersByOrder(order[0]).stream()
                                .map((offer) -> offer.getOrder().getId()).toList().size() == 3,

                        () -> String.valueOf(service.showOffersByOrder(order[0]).size() == 3))

        );
    }

    @Test
    void chooseOffer() {
        offerTest[0][1].getOrder().setOrderType(OrderType.WAITING_EXPERT_SELECTION);
        service.chooseOffer(offerTest[0][1]);
        assertAll(
                () -> assertEquals(Objects.requireNonNull(service.findById(offerTest[0][1].getId())).getOrder()
                                .getOrderType(), OrderType.WAITING_FOR_COME_TO_YOUR_PLACE),

                () -> assertTrue(offerTest[0][1].isChoose())
        );
    }

    @Test
    void findByOrder() {

        assertNotNull(service.findById(1L));

        assertEquals(3, service.findByOrder(order[0]).size());
    }

    @Test
    void findByOrderIdSortedPrice() {
        assertNotNull(service.findById(1L));
        assertEquals(service.findByOrder(offerTest[3][2].getOrder()).stream()
                        .map((Offer::getSuggestedPrice)).sorted(Comparator.reverseOrder()).toList().get(1)
                , service.findByOrderIdSortedPrice(offerTest[3][2].getOrder().getId(), user[3].getId()).stream()
                        .map(Offer::getSuggestedPrice).toList().get(1));
    }

    @Test
    void findByOrderIdSortedPoint() {
        List<Double> experts1 = new java.util.ArrayList<>(service.findByOrder(offerTest[3][0].getOrder())
                .stream().map(offer1 -> offer1.getExpert().getAverageScore()).toList());
        Collections.sort(experts1);
        assertEquals(experts1.get(0)
                , service.findByOrderIdSortedByPoint(offerTest[3][0].getOrder().getId(), user[3].getId()).stream()
                        .map(offer -> offer.getExpert().getAverageScore()).toList().get(0));

    }
}

