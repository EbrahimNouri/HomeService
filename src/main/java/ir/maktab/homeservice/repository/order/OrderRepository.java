package ir.maktab.homeservice.repository.order;

import ir.maktab.homeservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    List<Order> findAllByOrderType_WaitingForTheSuggestionsOrOrderType_WaitingExpertSelection();

}
