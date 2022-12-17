package ir.maktab.homeservice.controller.offer;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/offer")
public class OfferControllerImpl implements OfferController {

    @Override
    public void offerRegistrationOrUpdate(Offer offer) {

    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {
        return null;
    }

    @Override
    public void chooseOffer(Offer offer) {

    }

    @Override
    public void startOfWork(Offer offer) {

    }

    @Override
    public void endOfTheWork(Offer offer) {

    }

    @Override
    public Optional<Offer> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public List<Offer> findByOrder(Order order) {
        return null;
    }

    @Override
    public List<Offer> findByOrderIdSortedPrice(Long OrderId) {
        return null;
    }
}
