package ir.maktab.homeservice.repository.offer;


import ir.maktab.homeservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OfferRepository extends JpaRepository<Offer, Long>, JpaSpecificationExecutor<Offer> {

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

    List<Offer> findOfferByExpertId(Long expertId);

    @Query("select o from Offer o where o.choose = true")
    List<Offer> getAllAcceptedOffersByUserId(Long userId);

    @Query("select o from Offer o where o.choose = true and o.order.orderType = \"PAID\"")
    List<Offer> getAllDoneOffersByUserId(Long userId);

    @Query("from Offer o where o.order.id = :order and o.choose = true ")
    List<Offer> findByOrderIdAndChoose(Long order);
}
