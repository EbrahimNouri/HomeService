package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OfferService {

    void save(Offer offer);

    List<Offer> findOfferByOrder_Id(Long orderId);

    void offerRegistrationOrUpdate(Offer offer);

    List<Offer> showOffersByOrder(Order order);

    void chooseOffer(Offer offer);

    Offer findById(Long id);

    List<Offer> findByOrder(Order order);

    List<Offer> findByOrderIdSortedPrice(Long OrderId, Long userId);

    List<Offer> findByOrderIdSortedByPoint(Long orderId, Long userId);
}
