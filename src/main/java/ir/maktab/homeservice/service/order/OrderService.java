package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.PaymentType;

import java.util.List;
import java.util.Map;

public interface OrderService {
    Order findById(Long id);

    void save(Order order);

    void orderRegistration(Order order);

    void startOfWork(Long order, User user);

    void endOfTheWork(Long order);

    void setOrderToDone(Long order);

    void choosePaymentMethod(Long orderId, PaymentType paymentType, User user);

    void setOrderToPaidAppPayment(Long orderId, User user);

    void setOrderToPaidOnlinePayment(Long orderId, User user, String card);

    Order findOrderEndWork(User user);

    List<Order> showOrderSuggestionOrSelection();

    List<Order> findByTypeService(TypeService typeService);

    int countOfOrdersByUserId(Long userId);

    List<Order> findByUserId(Long id);

    List<Order> findBySpecification(Map<String, String> map);

    List<Order> findOrdersByTypeServices(List<TypeService> typeServices);
}
