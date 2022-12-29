package ir.maktab.homeservice.repository.order;

import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

    @Query("select od from Order od where od.orderType = \"WAITING_FOR_THE_SUGGESTIONS\" " +
            "OR od.orderType = \"WAITING_EXPERT_SELECTION\"")
    List<Order> findByOrderTypeBeforeStart();

    @Query("from Order od where od.orderType = \"WAITING_FOR_THE_SUGGESTIONS\"" +
            " OR od.orderType = \"WAITING_EXPERT_SELECTION\" and od.typeService = :typeService")
    List<Order> findByTypeService(TypeService typeService);

    Optional<Order> findOrderByUserAndOrderType(User user, OrderType orderType);

}
