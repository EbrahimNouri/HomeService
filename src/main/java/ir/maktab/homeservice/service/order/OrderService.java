package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import jakarta.transaction.Transactional;

import java.util.List;

public interface OrderService {
    Order findById(Long id);

    void save(Order order);

    void orderRegistration(Order order);

    void startOfWork(Order order);

    void endOfTheWork(Order order);

    void setOrderToDone(Order order);

    @Transactional
    void setOrderToPaidAppPayment(Order order);

    @Transactional
    void setOrderToPaidOnlinePayment(Order order);

    List<Order> showOrderSuggestionOrSelection();

    List<Order> findByTypeService(TypeService typeService);
}
