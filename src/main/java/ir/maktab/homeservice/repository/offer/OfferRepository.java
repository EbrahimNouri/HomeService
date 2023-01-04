package ir.maktab.homeservice.repository.offer;


import ir.maktab.homeservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findOfferByOrder_Id(Long order);

    @Query("from Offer o where o.order.id =:orderId order by o.suggestedPrice ASC ")
    List<Offer> findOfferSortedByPrice(Long orderId);

    Optional<Offer> findOfferById(Long id);

    @Query("from Offer o where o.order.id =:orderId order by o.expert.averageScore DESC ")
    List<Offer> findByOrderIdSortedByPoint(Long orderId);

    @Query("select count(o) from Offer o where o.expert = ?1")
    int countOfOffers(Long expertId);

    @Query("select count(o.order)from Offer o where o.expert= ?1 and o.order.orderType = \"PAID\"")
    int countOffOrderToDone(Long expertId);
}
