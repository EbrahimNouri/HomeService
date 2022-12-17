package ir.maktab.homeservice.controller.order;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.service.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderControllerImpl implements OrderController {

    private OrderService service;

    @Override
    @PostMapping("/orderRegistration")
    public void orderRegistration(Order order) {
        service.orderRegistration(order);
    }

    @Override
    public void setOrderToDone(Order order) {

    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return null;
    }
}
