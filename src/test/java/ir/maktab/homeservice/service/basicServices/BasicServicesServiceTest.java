package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BasicServicesServiceTest {

    private static BasicService[] basicService = new BasicService[5];
    private static TypeService[] typeServices = new TypeService[5];

    @Autowired
    private BasicServicesService service;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private BasicServiceRepository basicServiceRepository;

    @Autowired
    private BasicServiceRepository repository;

    @Autowired
    private TypeServiceService typeServiceService;
    @Autowired
    private TypeServiceRepository typeServiceRepository;


    @BeforeAll
    static void initialise() {
        for (int i = 0; i < 5; i++) {
            basicService[i] = new BasicService("basicNameTest" + i+1, null);
//            typeServices[i] = new TypeService("typeNameTest"+i+1, 150.0,null, null, basicService[i]);
        }
    }

    @BeforeEach
    void setToDataBase() {
        for (int i = 0; i < 5; i++) {
            service.addBasicService(basicService[i]);
//            typeServiceRepository.save(typeServices[i]);
        }
    }

//    @AfterEach
//    void purgeDb() {
//        for (int i = 0; i < 5; i++) {
//            basicServiceRepository.delete(basicService[i]);
//        }
//    }
//
//    @AfterAll
//    static void purgeOb() {
//        for (int i = 0; i < 5; i++) {
//            basicService[i] = null;
//        }
//    }

//    @AfterEach
//    void removeFromDatabase() {
//        for (int i = 0; i < 5; i++) {
//            repository.delete(basicService[i]);
//            typeServiceRepository.delete(typeServices[i]);
//        }
//    }


    @Test
    void addBasicService() {
        BasicService basicServiceAdd = new BasicService("basicNameTestAdd", null);

        service.addBasicService(basicServiceAdd);
        assertNotNull(Objects.requireNonNull(repository.findById(basicServiceAdd.getId()).orElse(null)).getId());
    }

    @Test
    void removeBasicService() {
        service.removeBasicService(basicService[4]);
        assertNull(repository.findBasicServiceById(basicService[4].getId()).orElse(null));
    }

    @Test
    void showAllBasicService() {

        List<BasicService> showAllBasicService = service.showAllBasicService();
        assertTrue(service.showAllBasicService().size() >= 5);
        service.showAllBasicService().forEach(System.out::println);
    }

    @Test
    void uniqueNameTest() {
        var save = BasicService.builder().name(basicService[1].getName()).build();
        service.addBasicService(save);

        assertNull(save.getId());
    }
}