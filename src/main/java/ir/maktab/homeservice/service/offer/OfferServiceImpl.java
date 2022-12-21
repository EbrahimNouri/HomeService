package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OfferServiceImpl implements OfferService {
    private final ExpertTypeServiceService expertTypeServiceService;
    private OfferRepository repository;
    private OrderService orderService;


    @Override
    public List<Offer> findOfferByOrder_Id(Long orderId) {
        return repository.findOfferByOrder_Id(orderId);
    }

    @Override
    public void offerRegistrationOrUpdate(Offer offer) {
        Order order = offer.getOrder();
        if (offerRegistrationCheck(offer, offer.getOrder())) {

            repository.save(offer);

            log.debug("debug offer registered {} ", order);
            if (order.getOrderType().equals(OrderType.WAITING_FOR_THE_SUGGESTIONS)
                    || order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION)) {

                order.setOrderType(OrderType.WAITING_EXPERT_SELECTION);
                orderService.save(order);

                log.debug("debug order change to {} ", order.getOrderType());
            } else
                log.debug("debug order type It had already changed {} ", order.getOrderType());

        } else {
            repository.save(offer);

            log.debug("debug offer updated {} ", order);
        }
    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {

        log.debug("debug found offer by order {}", order);
        return repository.findOfferByOrder_Id(order.getId());

    }

    @Override
    public void chooseOffer(Offer offer) {
        Order order = offer.getOrder();

        if (orderService.findById(order.getId()).orElseThrow(() -> new CustomExceptionNotFind("order Not found"))
                .getOffers().stream().anyMatch(offer1 -> !offer1.isChoose())) {

            if (checkLevelWork(offer, OrderType.WAITING_FOR_THE_SUGGESTIONS, OrderType.WAITING_EXPERT_SELECTION)) {

                offer.setChoose(true);
                repository.save(offer);

                order.setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
                orderService.save(order);

                log.debug("debug choose offer {} ", offer);
            } else
                throw new CustomExceptionUpdate("order type invalid");

        } else
            throw new CustomExceptionUpdate("user chosen a offer before it");
    }

    @Override
    public void startOfWork(Offer offer) {
        Order order = offer.getOrder();
        if (checkLevelWork(offer, OrderType.WAITING_FOR_COME_TO_YOUR_PLACE)) {

            order.setOrderType(OrderType.STARTED);
            orderService.save(order);
            repository.save(offer);

            log.debug("debug start of work {} ", order);
        } else {
            log.warn("warn start of work not worked order id or offer id is null or order type is invalid {} "
                    , offer);
            throw new CustomExceptionUpdate("this order not valid");

        }
    }

    @Override
    public void endOfTheWork(Offer offer) {
        Order order = offer.getOrder();
        try {
            if (checkLevelWork(offer, OrderType.STARTED)) {
                if (LocalDateTime.now().isAfter(offer.getEndDate())) {

                    offer.setDelay(ChronoUnit.HOURS.between(LocalDateTime.now(), offer.getEndDate()));
                    repository.save(offer);

                    log.debug("debug done of work {} ", order);
                }

                order.setOrderType(OrderType.DONE);
                orderService.save(order);

            }
        } catch (Exception e) {
            log.warn("warn done work not worked order id or offer id is null or order type is invalid {} "
                    , offer);
            throw new CustomExceptionUpdate("this order not valid");
        }

    }

    @Override
    public Optional<Offer> findById(Long id) {

        return repository.findOfferById(id);

    }

    @Override
    public List<Offer> findByOrder(Order order) {

        return repository.findOfferByOrder_Id(order.getId()/*, Sort.by(Sort.Direction.DESC*/);

    }

    @Override
    public List<Offer> findByOrderIdSortedPrice(Long orderId) {

        return repository.findOfferSortedByPrice(orderId);

    }

    @Override
    public List<Offer> findByOrderIdSortedByPoint(Long orderId) {

        return repository.findByOrderIdSortedByPoint(orderId);

    }


    public List<Offer> findByOrderIdSortedPoint(Long orderId) {

        return repository.findOfferSortedByPrice(orderId);

    }


    private boolean checkLevelWork(Offer offer, OrderType... orderType) {
        Order order = offer.getOrder();
        return order.getOrderType().equals(orderType[0])
                || order.getOrderType().equals(orderType[1])
                && order.getId() != null && offer.getId() != null
                && offer.getStartDate().isAfter(LocalDateTime.now());
    }

    private boolean offerRegistrationCheck(Offer offer, Order order) {
        return offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && order.getId() != null
                && offer.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && !expertTypeServiceService.findExpertTypeServiceByExpertId(offer.getExpert().getId()).stream()
                .filter(expertTypeService -> expertTypeService.getTypeService()
                        .getSubService().equals(order.getTypeService().getSubService())).toList().isEmpty();
    }

}
