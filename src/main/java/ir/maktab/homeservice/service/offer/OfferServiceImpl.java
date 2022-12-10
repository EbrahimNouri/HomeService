package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.repository.order.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OfferServiceImpl implements OfferService {
    private OfferRepository repository;
    private OrderRepository orderRepository;

    @Override
    public void offerRegistrationOrUpdate(Offer offer, Order order) {


        offer.setOrder(order);
        try {

            if (offerRegistrationCheck(offer, order)) {

                if (repository.findById(offer.getId()).isEmpty()) {

                    repository.save(offer);

                    log.debug("debug offer registered {} ", order);
                    if (order.getTypeService().equals(OrderType.WAITING_FOR_THE_SUGGESTIONS)) {
                        orderRepository.save(order);

                        log.debug("debug order change to {} ", order.getOrderType());
                    }else
                        log.debug("debug order type It had already changed {} ", order.getOrderType());

                } else {
                    repository.save(offer);

                    log.debug("debug offer updated {} ", order);
                }
            }else
                log.warn("offer or order not invalid");
        } catch (Exception e) {

            log.error("error offer updated {} ", order, e);
        }
    }

    private boolean offerRegistrationCheck(Offer offer, Order order){
        return offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && order.getId() != null
                && offer.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && offer.getExpert().getExpertTypeServices().stream()
                .filter(typeService -> typeService.getTypeService()
                        .getSubService().equals(order.getTypeService().getSubService())).toList().size()>0;
    }
}
