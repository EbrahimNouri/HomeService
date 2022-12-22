package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OrderService {
    Optional<Order> findById(Long id);

    void save(Order order);

    void orderRegistration(Order order);

    void startOfWork(Order order);

/*
    void chooseOffer(Order order);
*/

    void endOfTheWork(Order order);

    void setOrderToDone(Order order);

    void setOrderToPaid(Order order);

    List<Order> showOrderSuggestionOrSelection();
}
