package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order findById(Long id);

    void save(Order order);

    void orderRegistration(Order order);

    void startOfWork(Order order);

    void endOfTheWork(Order order);

    void setOrderToDone(Order order);

    @Transactional
    void setOrderToPaidAppPayment(Order order, User user);

    @Transactional
    void setOrderToPaidOnlinePayment(Order order);

    Order findOrderEndWork(User user);

    List<Order> showOrderSuggestionOrSelection();

    List<Order> findByTypeService(TypeService typeService);

    int countOfOrdersByUserId(Long userId);

    List<Order> findByUserId(Long id);

    List<Order> findBySpecification(Map<String, String> map);
}
