package ir.maktab.homeservice.service.typeService;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TypeServiceServiceImplTest {
    private static TypeService typeService;
    private static BasicService basicService;

    @Autowired
    TypeServiceService service;
    @Autowired
    TypeServiceService typeServiceservice;
    @Autowired
    BasicServicesService basicServiceService;

    @BeforeAll
    static void initialize() {
        basicService = BasicService.builder().name("testBaseName").build();
        typeService = TypeService.builder().basicService(basicService).subService("typName").build();
    }

    @BeforeEach
    void setToDatabase() {

        basicServiceService.save(basicService);
        typeServiceservice.save(typeService);

    }

    @AfterAll
    static void purgeObj() {
        basicService = null;
        typeService = null;
    }


    @Test
    void addSubService() {
        assertEquals(typeService.getSubService()
                , typeServiceservice.findByBasicServiceId(basicService.getId()).get(0).getSubService());
    }

    @Test
    void paymentPriceChange() {
        service.paymentPriceChange(typeService, 120);
        assertEquals(120, Objects.requireNonNull(typeServiceservice
                .findById(typeService.getId())));
    }

    @Test
    void showTypeServices() {
        assertEquals(typeService, service.showTypeServices(basicService.getId()).get(0));
        service.showTypeServices(basicService.getId()).forEach(System.out::println);

    }

    @Test
    void changeDescription(){
        String newDescription = "newDescription";
        service.descriptionChange(typeService.getId(), newDescription);
        assertEquals(newDescription
                , Objects.requireNonNull(service.findById(typeService.getId())).getDescription());

    }
}