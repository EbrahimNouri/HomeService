package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.config.ApplicationContextProvider;
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
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OfferServiceImpl implements OfferService {
    private final ExpertTypeServiceService expertTypeServiceService;
    private OfferRepository repository;
    private ApplicationContextProvider applicationContextProvider;

    @Override
    public void save(Offer offer) {
        try {
            repository.save(offer);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Offer> findOfferByOrder_Id(Long orderId) {
        try {
            return repository.findOfferByOrder_Id(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void offerRegistrationOrUpdate(Offer offer) {
        OrderService orderService = applicationContextProvider.getContext().getBean(OrderService.class);
        Order order = offer.getOrder();
        try {

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
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {
        try {
            log.debug("debug found offer by order {}", order);
            return repository.findOfferByOrder_Id(order.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public void chooseOffer(Offer offer) {
        OrderService orderService = applicationContextProvider.getContext().getBean(OrderService.class);

        Order order = offer.getOrder();
        try {
            if (orderService.findById(order.getId())
                    .getOffers().stream().anyMatch(offer1 -> !offer1.isChoose())) {

                if (order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION)) {
                    if (checkLevelWork(offer)) {

                        offer.setChoose(true);
                        repository.save(offer);

                        order.setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
                        orderService.save(order);

                        log.debug("debug choose offer {} ", offer);
                    } else throw new CustomExceptionUpdate("check level work error");

                } else
                    throw new CustomExceptionUpdate("order type invalid");

            } else
                throw new CustomExceptionUpdate("user chosen a offer before it");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public Offer findById(Long id) {

        return repository.findOfferById(id).orElseThrow(() -> new CustomExceptionNotFind("offer not found"));
    }

    @Override
    public List<Offer> findByOrder(Order order) {
        try {
            return repository.findOfferByOrder_Id(order.getId()/*, Sort.by(Sort.Direction.DESC*/);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Offer> findByOrderIdSortedPrice(Long orderId) {
        try {
            return repository.findOfferSortedByPrice(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

    @Override
    public List<Offer> findByOrderIdSortedByPoint(Long orderId) {
        try {
            return repository.findByOrderIdSortedByPoint(orderId);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }


    public List<Offer> findByOrderIdSortedPoint(Long orderId) {
        try {
            return repository.findOfferSortedByPrice(orderId);

        } catch (Exception e) {
            e.printStackTrace();

            return new ArrayList<>();
        }
    }

    private boolean checkLevelWork(Offer offer) {
        Order order = offer.getOrder();
        return order.getId() != null && offer.getId() != null
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
