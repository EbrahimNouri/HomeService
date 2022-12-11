package ir.maktab.homeservice.controller.order;


import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OrderController {
    void OrderRegistration(Order order);

    List<Order> showOrderSuggestionOrSelection();
}
