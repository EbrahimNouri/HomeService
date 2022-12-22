package ir.maktab.homeservice.service.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class BasicServicesServiceTest {

    private static BasicService[] basicService = new BasicService[5];

    @Autowired
    private BasicServicesService service;
    @Autowired
    private BasicServiceRepository basicServiceRepository;
    @Autowired
    private BasicServiceRepository repository;


    @Test
    @Order(1)
    void addBasicService() {
        basicService[0] = new BasicService("basicNameTestxc23", null);
        service.addBasicService(basicService[0]);
        assertNotNull(basicService[0].getId());
    }

    @Test
    @Order(4)
    void removeBasicService() {
        basicService[1] = new BasicService("basicNameTeskltxc23", null);

        assertAll(
                () -> service.addBasicService(basicService[1]),
                () -> assertTrue(service.checkByName(basicService[1].getName())),
                () -> repository.delete(basicService[1]),
                () -> assertFalse(service.checkByName(basicService[1].getName()))
        );
    }

    @Test
    @Order(3)
    void showAllBasicService() {
        for (int i = 0; i < 5; i++) {
            service.addBasicService(new BasicService("qwert" + i, null));
        }
        assertEquals(5, service.showAllBasicService().size());
        service.showAllBasicService().forEach(System.out::println);
    }

    @Test
    @Order(2)
    void uniqueNameTest() {
        BasicService tempBase = new BasicService("basicName78Teskltxc23", null);
        assertAll(

                () -> basicService[2] = new BasicService("basicName78Teskltxc23", null),

                () -> service.addBasicService(basicService[2]),

                () -> assertTrue(service.checkByName(basicService[2].getName())),

/*                () -> assertThrows(CustomExceptionSave.class
                        , () -> service.addBasicService(tempBase)
                        , "this name is invalid"),*/

                () -> assertNull(tempBase.getId())
        );
    }
}