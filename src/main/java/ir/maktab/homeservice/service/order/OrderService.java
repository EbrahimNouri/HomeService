package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OrderService {
    void OrderRegistration(Order order);

    void setOrderToDone(Order order);

    void setOrderToPaid(Order order);

    List<Order> showOrderSuggestionOrSelection();
}
