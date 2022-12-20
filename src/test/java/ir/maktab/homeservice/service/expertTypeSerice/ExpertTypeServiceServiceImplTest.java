package ir.maktab.homeservice.service.expertTypeSerice;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import org.junit.jupiter.api.*;
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
    ExpertRepository expertRepository;
    @Autowired
    BasicServiceRepository basicServiceRepository;
    @Autowired
    TypeServiceRepository typeServiceRepository;
    @Autowired
    private ExpertTypeServiceRepository expertTypeServiceRepository;


    @BeforeAll
    static void initialize() {
        basicService = BasicService.builder().name("testBasicService").build();
        expert = Expert.builder().firstname("testName").lastname("testLastname").email("test@email.com")
                .password("1234QWer").build();
        avatar = new File("/Users/ebrahimnouri/Downloads/unr_test_180821_0925_9k0pgs.jpg");
        typeService = TypeService.builder().subService("subServiceTest").basicService(basicService).build();
        expertTypeService = ExpertTypeService.builder().expert(expert).typeService(typeService).build();
    }

    @BeforeEach
    void setToDb() {
        expert.setId(null);
        basicService.setId(null);
        typeService.setId(null);
        expertService.registerExpert(expert, avatar);
        basicServicesService.addBasicService(basicService);
        typeServiceService.addSubService(typeService);
    }


    @AfterEach
    void purgeDatabase() {
        expertTypeServiceRepository.delete(expertTypeService);
        expertRepository.delete(expert);
        typeServiceRepository.delete(typeService);
        basicServiceRepository.delete(basicService);
    }

    @AfterAll
    static void purgeObj() {
        expert = null;
        basicService = null;
        typeService = null;
        expertTypeService = null;
    }


    @Test
    void removeExpertFromBasicService() {
        service.addExpertToTypeService(new ExpertTypeService(expert, typeService));
        assertAll(
                () -> assertNotNull(expert.getId()),
                () -> assertNotNull(typeService.getId()),
                () -> assertNotNull(service.findById(new ExpertTypeServiceId(expert, typeService))),
                () -> service.removeExpertFromBasicService(expert),
                () -> assertNull(service.findById(new ExpertTypeServiceId(expert, typeService)).orElse(null))
        );
    }


    @Test
    void addExpertToTypeService() {
        service.addExpertToTypeService(expertTypeService);
        assertNotNull(service.findById(new ExpertTypeServiceId(expert, typeService)).orElse(null));
    }

    @Test
    void removeExpertFromTypeService() {
        assertAll(
                () -> service.addExpertToTypeService(expertTypeService),
                () -> assertNotNull(service.findById(new ExpertTypeServiceId(expert, typeService))),
                () -> service.removeExpertFromTypeService(expertTypeService),
                () -> assertNull(service.findById(new ExpertTypeServiceId(expert, typeService)).orElse(null))
        );
    }
}

