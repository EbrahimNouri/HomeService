package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OfferService {
    void offerRegistrationOrUpdate(Offer offer, Order order);

    List<Offer> showOffersByOrder(Order order);

    void chooseOffer(Offer offer);

    void startedToWord(Offer offer);
}
