package ir.maktab.homeservice.controller.order;


import ir.maktab.homeservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderControllerImpl implements OrderController {

    @Override
    public void OrderRegistration(Order order) {

    }

    @Override
    public void setOrderToDone(Order order) {

    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return null;
    }
}
