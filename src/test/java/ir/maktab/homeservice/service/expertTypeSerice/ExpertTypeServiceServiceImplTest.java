package ir.maktab.homeservice.service.expertTypeSerice;

import ir.maktab.homeservice.HomeServiceApplication;
import ir.maktab.homeservice.service.basicServices.BasicServicesServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

//@RunWith(SpringRunner.class)
//@SpringBootTest(
//        classes = BasicServicesServiceImpl.class)
//@AutoConfigureMockMvc
//@TestPropertySource(
//        locations = "classpath:applicationTest.properties")



//@Import({BasicServicesServiceImpl.class, BasicServiceRepository.class})

@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
@PropertySource("applicationTest.properties")
@ExtendWith(MockitoExtension.class)
class ExpertTypeServiceServiceImplTest {

    @Autowired
    ExpertTypeServiceService service;

    @Test
    void removeExpertFromBasicService() {

    }
    @Test
    void addExpertToTypeService() {
    }

    @Test
    void removeExpertFromTypeService() {
    }
}