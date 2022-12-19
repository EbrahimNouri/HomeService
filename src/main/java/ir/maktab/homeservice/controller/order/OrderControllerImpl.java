package ir.maktab.homeservice.controller.order;


import ir.maktab.homeservice.dto.OrderDto;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/order")
public class OrderControllerImpl implements OrderController {
    private final UserService userService;

    private OrderService service;

    @Override
    @PostMapping("/orderRegistration")
    public void orderRegistration(@RequestBody OrderDto orderDto) {

        Order order = Order.builder()
                .orderType(OrderType.WAITING_FOR_THE_SUGGESTIONS)
                .address(orderDto.getAddress())
                .description(orderDto.getDescription())
                .user(userService.findById(orderDto.getUserId()).orElseThrow(
                        () -> new CustomExceptionNotFind("user not found")))
                .startOfWork(orderDto.getStartOfWork())
                .suggestedPrice(orderDto.getPrice())
                .build();

        service.orderRegistration(order);
    }

    @PutMapping("/setOrderToDone/{orderId}")
    @Override
    public void setOrderToDone(@PathVariable Long orderId) {
        Order order = service.findById(orderId).orElseThrow(() -> new CustomExceptionNotFind("order not found."));
        service.setOrderToDone(order);
    }

    @GetMapping("/showOrderSuggestionOrSelection")
    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return service.showOrderSuggestionOrSelection();
    }
}
