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
            basicService[i] = new BasicService("hello" + i, null);
            typeServices[i] = new TypeService("ss"+i, 150.0,null, null, basicService[i]);
        }
    }

    @BeforeEach
    void setToDataBase() {
        for (int i = 0; i < 5; i++) {
            service.addBasicService(basicService[i]);
            typeServiceRepository.save(typeServices[i]);
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
        assertNotEquals(basicService[0],repository.findBasicServiceById(basicService[0].getId()).orElse(null));
    }

    @Test
    void removeBasicService() {
        service.removeBasicService(basicService[3]);
        assertNull(repository.findBasicServiceById(basicService[3].getId()).orElse(null));
    }

    @Test
    void showAllBasicService() {

        List<BasicService> showAllBasicService = service.showAllBasicService();
        assertEquals(5, service.showAllBasicService().size());
        service.showAllBasicService().forEach(System.out::println);
    }

    @Test
    void uniqueNameTest() {
        var save = BasicService.builder().name("hello1").build();
        service.addBasicService(save);

        assertNull(save.getId());
    }
}