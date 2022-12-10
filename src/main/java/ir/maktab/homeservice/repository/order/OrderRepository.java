package ir.maktab.homeservice.repository.order;

import ir.maktab.homeservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select od from Order od where od.orderType = \"WAITING_FOR_THE_SUGGESTIONS\" and od.orderType = \"WAITING_EXPERT_SELECTION\"")
    List<Order> findByOrderTypeBeforeStart();

}
