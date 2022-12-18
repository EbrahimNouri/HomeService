package ir.maktab.homeservice.controller.order;


import ir.maktab.homeservice.dto.OrderDto;
import ir.maktab.homeservice.entity.Order;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface OrderController {

    @PostMapping("/orderRegistration")
    public void orderRegistration(@RequestBody OrderDto orderDto);
    @PutMapping("/setOrderToDone/{orderId}")
    public void setOrderToDone(@PathVariable Long orderId);
    List<Order> showOrderSuggestionOrSelection();
}
