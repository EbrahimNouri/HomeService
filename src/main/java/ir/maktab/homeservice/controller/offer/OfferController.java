package ir.maktab.homeservice.controller.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;

import java.util.List;

public interface OfferController {
    void offerRegistrationOrUpdate(Offer offer, Order order);

    List<Offer> showOffersByOrder(Order order);

    void chooseOffer(Offer offer);

    void startOfWork(Offer offer);

    void endOfTheWork(Offer offer);
}