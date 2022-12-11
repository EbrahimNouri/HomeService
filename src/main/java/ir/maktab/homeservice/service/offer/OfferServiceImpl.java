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

import java.time.LocalDate;
import java.util.List;

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
                    if (order.getOrderType().equals(OrderType.WAITING_FOR_THE_SUGGESTIONS)) {
                        orderRepository.save(order);

                        log.debug("debug order change to {} ", order.getOrderType());
                    } else
                        log.debug("debug order type It had already changed {} ", order.getOrderType());

                } else {
                    repository.save(offer);

                    log.debug("debug offer updated {} ", order);
                }
            } else
                log.warn("offer or order not invalid");
        } catch (Exception e) {

            log.error("error offer updated {} ", order, e);
        }
    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {
        try {
            log.debug("debug found offer by order {}", order);

            return repository.findByOrder(order);

        } catch (Exception e) {
            log.error("error found offer by order {} ", order, e);
        }
        return null;
    }

    @Override
    public void chooseOffer(Offer offer) {
        Order order = offer.getOrder();

        if (checkLevelWork(offer, OrderType.WAITING_FOR_THE_SUGGESTIONS)) {

            try {

                order.setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
                orderRepository.save(order);

                log.debug("debug choose offer {} ", offer);
            } catch (Exception e) {
                log.error("error choose offer {} ", offer, e);
            }
        }
    }

    @Override
    public void startOfWork(Offer offer) {
        Order order = offer.getOrder();
        try {
            if (checkLevelWork(offer, OrderType.WAITING_FOR_COME_TO_YOUR_PLACE)) {

                orderRepository.save(order);

                log.debug("debug start of work {} ", order);
            } else
                log.warn("warn start of work not worked order id or offer id is null or order type is invalid {} "
                        , offer);
        } catch (Exception e) {
            log.error("error start of work {} ", offer, e);
        }
    }

    @Override
    public void endOfTheWork(Offer offer) {
        Order order = offer.getOrder();
        try {
            if (checkLevelWork(offer, OrderType.DONE)) {

                orderRepository.save(order);

                log.debug("debug done of work {} ", order);
            } else
                log.warn("warn done work not worked order id or offer id is null or order type is invalid {} "
                        , offer);
        } catch (Exception e) {
            log.error("error done of work {} ", offer, e);
        }
    }

    private boolean checkLevelWork(Offer offer, OrderType orderType) {
        Order order = offer.getOrder();
        return order.getOrderType().equals(orderType)
                && order.getId() != null && offer.getId() != null
                && LocalDate.now().isAfter(offer.getStartDate());
    }

    private boolean offerRegistrationCheck(Offer offer, Order order) {
        return offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && order.getId() != null
                && offer.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && offer.getExpert().getExpertTypeServices().stream()
                .filter(typeService -> typeService.getTypeService()
                        .getSubService().equals(order.getTypeService().getSubService())).toList().size() > 0;
    }
}
