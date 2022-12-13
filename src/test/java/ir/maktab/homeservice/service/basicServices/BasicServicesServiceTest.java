package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BasicServicesServiceTest {

    private static BasicService[] basicService = new BasicService[5];
    @Autowired
    private BasicServicesService service;
    @Autowired
    private OfferRepository offerRepository;
    @Autowired
    private BasicServiceRepository basicServiceRepository;

    @Autowired
    private BasicServiceRepository repository;


    @BeforeAll
    static void initialise() {
        for (int i = 0; i < 5; i++) {
            basicService[i] = new BasicService("hello" + i, null);
        }
    }

    @BeforeEach
    void setToDataBase() {
        for (int i = 0; i < 5; i++) {
            service.addBasicService(basicService[i]);
        }
    }

    @AfterEach
    void purgeDb() {
        for (int i = 0; i < 5; i++) {
            basicServiceRepository.delete(basicService[i]);
        }
    }

    @AfterAll
    static void purgeOb() {
        for (int i = 0; i < 5; i++) {
            basicService[i] = null;
        }
    }

    @AfterEach
    void removeFromDatabase() {
        for (int i = 0; i < 5; i++) {
            repository.delete(basicService[i]);
        }
    }


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
        assertEquals(5, showAllBasicService.size());
    }

    @Test
    void uniqueNameTest() {
        var save = BasicService.builder().name("hello1").build();
        service.addBasicService(save);

        assertNull(save.getId());
    }
}