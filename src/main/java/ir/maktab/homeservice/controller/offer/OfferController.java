package ir.maktab.homeservice.controller.offer;

import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.entity.Offer;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;

import java.util.List;

public interface OfferController {

    void offerRegistrationOrUpdate(OfferDto offerDto);

    @GetMapping("/showOffersByOrder/{orderId}")
    List<Offer> showOffersByOrder(@PathVariable Long orderId);
    @PutMapping ("/chooseOffer/{offerId}")
    void chooseOffer(@PathVariable Long offerId);

    @PutMapping("/startOfWork/{offerId}")
    void startOfWork(@PathVariable Long offerId);

    @PutMapping("/endOfTheWork/{offerId}")
    void endOfTheWork(@PathVariable Long offerId);
    @GetMapping("/{id}")
    ResponseEntity<Offer> findById(@PathVariable Long id);

    List<Offer> findByOrderIdSortedPrice(Long OrderId);
}
