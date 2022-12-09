package ir.maktab.homeservice.service.offer;

import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;

public interface OfferService {
    void offerRegistrationOrUpdate(Offer offer, Order order);
}
