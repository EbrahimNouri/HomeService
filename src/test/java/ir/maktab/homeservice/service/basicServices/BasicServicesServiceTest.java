package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.util.List;


@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
@PropertySource("applicationTest.properties")
class BasicServicesServiceTest {

    static BasicService basicService;
    @Autowired
    private BasicServicesService service;

    @Autowired
    private BasicServiceRepository repository;


    @BeforeAll
    static void initialise() {
        basicService = new BasicService("hello", null);
    }


    @Test
    void addBasicService() {
        service.addBasicService(basicService);
        Assertions.assertEquals(basicService.getName()
                , repository.findBasicServiceById(1L).orElseThrow().getName());
    }

    @Test
    void removeBasicService() {
        service.removeBasicService(basicService);
        Assertions.assertNull(basicService.getId());
    }

    @Test
    void showAllBasicService() {
        service.addBasicService(BasicService.builder().name("testForAll1").build());
        service.addBasicService(BasicService.builder().name("testForAll2").build());
        service.addBasicService(BasicService.builder().name("testForAll3").build());
        List<BasicService> showAllBasicService = service.showAllBasicService();
        Assertions.assertEquals(3, showAllBasicService.size());
    }

    @Test
    void uniqueNameTest(){
        service.addBasicService(basicService);
        BasicService basicService1 = BasicService.builder().name("hello").build();
        service.addBasicService(basicService1);
        Assertions.assertNotNull(basicService.getId());
    }
}