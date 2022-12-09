package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OfferServiceImpl implements OfferService {
    private OfferRepository repository;

    @Override
    public void offerRegistrationOrUpdate(Offer offer, Order order) {


        offer.setOrder(order);

        if (offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()) {

            if (repository.findById(offer.getId()).isEmpty()) {

                repository.save(offer);

            } else {
                repository.save(offer);
            }
        }


    }
}
