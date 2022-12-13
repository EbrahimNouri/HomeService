package ir.maktab.homeservice.service.order;

import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.user.UserService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class OrderServiceImplTest {

    static User user;
    static Order order;
    @Autowired
    private OrderService service;
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private UserService userService;

    @BeforeAll
    static void initials() {
        user = User.builder().firstname("name").lastname("name").email("userTest@email.com").credit(500.0).password("1234QWear").build();

        order = new Order(null, user, null, null, 100.0
                , "test", LocalDate.now(), "test", null);
    }

    @BeforeEach
    void addToDatabase() {
        userService.registerUser(user);
    }

    @Test
    void orderRegistration() {
        service.OrderRegistration(order);
        assertEquals(order.getDescription(), Objects.requireNonNull
                (orderRepository.findById(order.getId()).orElse(null)).getDescription());
    }

    @Test
    void showOrderSuggestionOrSelection() {
        assertFalse(
                () -> service.showOrderSuggestionOrSelection().stream().anyMatch((order) ->
                        order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION)
                                || order.getOrderType().equals(OrderType.DONE)
                                || order.getOrderType().equals(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE)
                                || order.getOrderType().equals(OrderType.PAID)
                                || order.getOrderType().equals(OrderType.STARTED)
                ));
    }

    @Test
    void setOrderToDone() {
        service.setOrderToDone(order);
        assertTrue(() -> order.getOrderType().equals(OrderType.DONE));
    }
}