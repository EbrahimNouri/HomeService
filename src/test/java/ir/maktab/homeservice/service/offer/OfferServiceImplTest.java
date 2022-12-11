package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.PropertySource;

import java.io.File;


@ComponentScan(basePackages = "ir.maktab.homeservice")
@SpringBootTest
@PropertySource("applicationTest.properties")
@ExtendWith(MockitoExtension.class)
class OfferServiceImplTest {

    private static Offer offer;

    @Autowired
    private OfferService service;

    @BeforeAll
    static void initialize() {

    }

    @BeforeEach
    void setToDb() {

    }

    @Test
    void offerRegistrationOrUpdate() {
    }

    @Test
    void showOffersByOrder() {
    }

    @Test
    void chooseOffer() {
    }

    @Test
    void startOfWork() {
    }

    @Test
    void endOfTheWork() {
    }
}