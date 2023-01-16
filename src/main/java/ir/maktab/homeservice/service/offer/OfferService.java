package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;

import java.util.List;
import java.util.Map;

public interface OfferService {

    void save(Offer offer);

    List<Offer> findOfferByOrder_Id(Long orderId);

    void offerRegistrationOrUpdate(Offer offer);

    List<Offer> showOffersByOrder(Order order);

    void chooseOffer(Offer offer, User user);

    Offer findById(Long id);

    List<Offer> findByOrder(Order order);

    List<Offer> findByOrderIdSortedPrice(Long OrderId, Long userId);

    List<Offer> findByOrderIdSortedByPoint(Long orderId, Long userId);

    int countOfOffers(Long expertId);

    int countOffOrderToDone(Long expertId);

    List<Offer> getAllAcceptedOffersByUserId(Long userId);

    List<Offer> getAllDoneOffersByUserId(Long userId);

    List<Offer> findByExpertId(Long expertId);

    List<Offer> offerSpecification(Map<String, String> map);

    Offer findByOrderIdChosen(Long orderId);
}
