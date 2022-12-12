package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;

import java.util.List;
import java.util.Optional;

public interface OfferService {
    void offerRegistrationOrUpdate(Offer offer);

    List<Offer> showOffersByOrder(Order order);

    void chooseOffer(Offer offer);

    void startOfWork(Offer offer);

    void endOfTheWork(Offer offer);

    Optional<Offer> findById(Long id);

    List<Offer> findByOrder(Order order);
}
