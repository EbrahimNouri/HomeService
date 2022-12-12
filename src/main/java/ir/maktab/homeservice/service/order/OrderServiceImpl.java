package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {

    private OrderRepository repository;

    @Override
    public void OrderRegistration(Order order) {
        try {
            if (order.getSuggestedPrice() != null
                    && order.getDescription() != null
                    && order.getStartOfWork() != null
                    && order.getUser() != null) {
// TODO: 12/12/2022 AD if 
                order.setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
                repository.save(order);

                log.debug("debug order registration {} ", order);
            } else {

                log.error("debug order registration the fields are not filled {} ", order);
            }
        } catch (Exception e) {

            log.error("debug order registration {} ", order);
        }
    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return repository.findByOrderTypeBeforeStart();
    }
}
