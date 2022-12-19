package ir.maktab.homeservice.controller.offer;


import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/offer")
public class OfferControllerImpl implements OfferController {

    private OfferService service;
    private ExpertService expertService;
    private OrderService orderService;

    @Override
    @PostMapping("/offerRegistrationOrUpdate")
    public void offerRegistrationOrUpdate(OfferDto offerDto) {

        Offer offer = Offer.builder()
                .expert(expertService.findById(offerDto.getExpertId())
                        .orElseThrow(() -> new CustomExceptionNotFind("expert not found")))

                .order(orderService.findById(offerDto.getOrderId())
                        .orElseThrow(() -> new CustomExceptionNotFind("order not found")))

                .description(offerDto.getDescription())
                .startDate(offerDto.getStartDate())
                .EndDate(offerDto.getEndDate())
                .build();

        service.offerRegistrationOrUpdate(offer);
    }

    @Override
    @GetMapping("/showOffersByOrder/{orderId}")
    public List<Offer> showOffersByOrder(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId)
                .orElseThrow(() -> new CustomExceptionNotFind("order not found"));

        return service.showOffersByOrder(order);
    }

    @Override
    @PutMapping("/chooseOffer/{offerId}")
    public void chooseOffer(@PathVariable Long offerId) {
        Offer offer = service.findById(offerId)
                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));

        service.chooseOffer(offer);
    }

    @Override
    @PutMapping("/startOfWork/{offerId}")
    public void startOfWork(@PathVariable Long offerId) {
        Offer offer = service.findById(offerId)
                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));

        service.startOfWork(offer);
    }

    @Override
    @PutMapping("/endOfTheWork/{offerId}")
    public void endOfTheWork(@PathVariable Long offerId) {
        Offer offer = service.findById(offerId)
                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));

        service.endOfTheWork(offer);
    }

    @Override
    @GetMapping("/{id}")
    public ResponseEntity<Offer> findById(@PathVariable Long id) {
        return service.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }


    @Override
    @GetMapping("/findByOrderIdSortedPrice/{orderId}")
    public List<Offer> findByOrderIdSortedPrice(@PathVariable Long orderId) {
        return service.findByOrderIdSortedPrice(orderId);
    }

    // TODO: 12/19/2022 AD find by findByOrderIdSortedPoint
}
