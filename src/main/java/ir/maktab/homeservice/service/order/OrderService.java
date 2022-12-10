package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OrderService {
    void OrderRegistration(Order order);

    List<Order> showOrderSuggestionOrSelection();
}
