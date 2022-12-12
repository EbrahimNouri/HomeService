package ir.maktab.homeservice.repository.offer;


import ir.maktab.homeservice.entity.Offer;

import ir.maktab.homeservice.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long> {

    List<Offer> findByOrder(Order order);

    Optional<Offer> findOfferById(Long id);

    List<Offer> findOfferByOrder_Id(Long order);

    @Query("from Offer o where o.order.id =:orderId order by o.suggestedPrice desc ")
    List<Offer> findOfferSortedByPrice(Long orderId);
}
