package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
public class OfferServiceImpl implements OfferService {
    private OfferRepository repository;

    @Override
    public void offerRegistrationOrUpdate(Offer offer, Order order) {


        offer.setOrder(order);
        try {

            if (offer.getEndDate().isAfter(offer.getStartDate())
                    && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()) {

                if (repository.findById(offer.getId()).isEmpty()) {

                    repository.save(offer);

                    log.debug("debug offer registered {} ", order);
                } else {
                    repository.save(offer);

                    log.debug("debug offer updated {} ", order);
                }
            }
        } catch (Exception e) {

            log.error("error offer updated {} ", order, e);
        }
    }
}
