package ir.maktab.homeservice.service.typeService;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TypeServiceServiceImplTest {
    private static TypeService typeService;
    private static BasicService basicService;

    @Autowired
    TypeServiceService service;
    @Autowired
    private BasicServiceRepository basicServiceRepository;
    @Autowired
    private TypeServiceRepository typeServiceRepository;

    @BeforeAll
    static void initialize() {
        basicService = BasicService.builder().name("testBaseName").build();
        typeService = TypeService.builder().basicService(basicService).subService("typName").build();
    }

    @BeforeEach
    void setToDatabase() {
        basicServiceRepository.save(basicService);
        typeServiceRepository.save(typeService);
    }

    @AfterAll
    static void purgeObj() {
        basicService = null;
        typeService = null;
    }

//    @AfterEach
//    void purgeDatabase() {
//        typeServiceRepository.delete(typeService);
//        basicServiceRepository.delete(basicService);
//    }

    @Test
    void addSubService() {
        assertEquals(typeService.getSubService()
                , typeServiceRepository.findByBasicServiceId(basicService.getId()).get(0).getSubService());
    }

    @Test
    void paymentPriceChange() {
        service.paymentPriceChange(typeService, 120);
        assertEquals(120, Objects.requireNonNull(typeServiceRepository
                .findById(typeService.getId()).orElse(null)).getBasePrice());
    }

    @Test
    void showTypeServices() {
        assertEquals(typeService, service.showTypeServices(basicService.getId()).get(0));
        service.showTypeServices(basicService.getId()).forEach(System.out::println);

    }
}