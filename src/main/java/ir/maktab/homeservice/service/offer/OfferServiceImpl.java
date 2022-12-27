package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.config.ApplicationContextProvider;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionOrderType;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.order.OrderService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

        repository.save(offer);

    }

    @Override
    public List<Offer> findOfferByOrder_Id(Long orderId) {

        return repository.findOfferByOrder_Id(orderId);

    }

    @Override
    public void offerRegistrationOrUpdate(Offer offer) {
        OrderService orderService = applicationContextProvider.getContext().getBean(OrderService.class);
        Order order = offer.getOrder();

        if (offerRegistrationCheck(offer, offer.getOrder())) {

            repository.save(offer);

            log.debug("debug offer registered {} ", order);
            if (!order.getOrderType().equals(OrderType.WAITING_FOR_THE_SUGGESTIONS)
                    || !order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION))

                throw new CustomExceptionOrderType("order type not valid");

            order.setOrderType(OrderType.WAITING_EXPERT_SELECTION);
            orderService.save(order);

            log.debug("debug order change to {} ", order.getOrderType());

        }

    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {

        log.debug("debug found offer by order {}", order);
        return repository.findOfferByOrder_Id(order.getId());

    }

    @Override
    public void chooseOffer(Offer offer) {
        OrderService orderService = applicationContextProvider.getContext().getBean(OrderService.class);

        Order order = offer.getOrder();

        if (!orderService.findById(order.getId())
                .getOffers().stream().anyMatch(offer1 -> !offer1.isChoose()))
            throw new CustomExceptionUpdate("user chosen a offer before it");

        if (!order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION))
            throw new CustomExceptionUpdate("order type invalid");

        if (!checkLevelWork(offer))
            throw new CustomExceptionUpdate("check level work error");

        offer.setChoose(true);
        repository.save(offer);

        order.setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
        orderService.save(order);

        log.debug("debug choose offer {} ", offer);


    }


    @Override
    public Offer findById(Long id) {

        return repository.findOfferById(id).orElseThrow(() -> new CustomExceptionNotFind("offer not found"));
    }

    @Override
    public List<Offer> findByOrder(Order order) {

        return repository.findOfferByOrder_Id(order.getId());

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

    private boolean checkLevelWork(Offer offer) {
        Order order = offer.getOrder();
        return order.getId() != null && offer.getId() != null
                && offer.getStartDate().isAfter(LocalDateTime.now());
    }


    private boolean offerRegistrationCheck(Offer offer, Order order) {
        if (offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && order.getId() != null
                && offer.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && !expertTypeServiceService.findExpertTypeServiceByExpertId(offer.getExpert().getId()).stream()
                .filter(expertTypeService -> expertTypeService.getTypeService()
                        .getSubService().equals(order.getTypeService().getSubService())).toList().isEmpty()) {
            return true;
        } else
            throw new CustomExceptionSave("offer registration not valid");
    }
}

