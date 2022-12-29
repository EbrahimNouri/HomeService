package ir.maktab.homeservice.service.expertTypeSerice;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ExpertTypeServiceServiceImplTest {

    private static ExpertTypeService expertTypeService;
    private static Expert expert;
    private static BasicService basicService;
    private static File avatar;
    private static TypeService typeService;

    @Autowired
    ExpertService expertService;

    @Autowired
    TypeServiceService typeServiceService;

    @Autowired
    ExpertTypeServiceService service;

    @Autowired
    BasicServicesService basicServicesService;

    @Autowired
    private ExpertTypeServiceService expertTypeServiceService;


    @BeforeAll
    static void initialize() {
        basicService = BasicService.builder().name("testBasicService").build();
        expert = Expert.builder().firstname("testName").lastname("testLastname").email("test1212@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        typeService = TypeService.builder().subService("subServiceTest").basicService(basicService)
                .description("description").build();
        expertTypeService = ExpertTypeService.builder().expert(expert).typeService(typeService).build();
    }

    @BeforeEach
    void setToDb() {
        expertService.registerExpert(expert, avatar);
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
    }


    @Test
    void removeExpertFromBasicService() {
        ExpertTypeService expppert = new ExpertTypeService(expert, typeService);
        service.addExpertToTypeService(expppert);
        assertAll(
                () -> assertNotNull(expppert.getExpert()),
                () -> assertNotNull(expppert.getTypeService()),
                () -> assertNotNull(service.findById(expert.getId(), typeService.getId()))
        );
    }


    @Test
    void addExpertToTypeService() {
        service.addExpertToTypeService(expertTypeService);
        assertNotNull(service.findById(expert.getId(), typeService.getId()));
    }

    @Test
    void removeExpertFromTypeService() {
        assertAll(
                () -> service.addExpertToTypeService(expertTypeService),
                () -> assertNotNull(service.findById(expert.getId(), typeService.getId())),
                () -> service.removeExpertFromTypeService(expertTypeService),
                () -> assertNull(service.findById(expert.getId(), typeService.getId()))
        );
    }
}

