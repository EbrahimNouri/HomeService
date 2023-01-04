package ir.maktab.homeservice.repository.order;

import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long>, JpaSpecificationExecutor<Order> {

    @Query("select od from Order od where od.orderType = \"WAITING_FOR_THE_SUGGESTIONS\" " +
            "OR od.orderType = \"WAITING_EXPERT_SELECTION\"")
    List<Order> findByOrderTypeBeforeStart();

    @Query("from Order od where od.orderType = \"WAITING_FOR_THE_SUGGESTIONS\"" +
            " OR od.orderType = \"WAITING_EXPERT_SELECTION\" and od.typeService.subService = :typeService")
    List<Order> findOrderByTypeService(TypeService typeService);

    Optional<Order> findOrderByUserAndOrderType(User user, OrderType orderType);

    @Query("from Order o where o.startOfWork >= :start and o.startOfWork < :end")
    List<Order> findOrdersByPriodTime(LocalDate start, LocalDate end);

    @Query("from Order o where o.typeService.basicService = ?1")
    List<Order> findByBasicServiceName(String basicService);

    @Query("from Order o where o.typeService.subService = ?1")
    List<Order> findOrderByTypeServiceName(String typeService);

    @Query("select count(o) from Order o where o.user.id = ?1")
    int countOfOrdersByUserId(Long userId);
}
