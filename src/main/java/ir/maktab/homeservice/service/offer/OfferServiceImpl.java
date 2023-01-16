package ir.maktab.homeservice.service.offer;


import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.repository.offer.OfferRepository;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.util.SpecificationUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OfferServiceImpl implements OfferService {
    private final ExpertTypeServiceService expertTypeServiceService;
    private final OfferRepository repository;
    private final ApplicationContext applicationContextProvider;
    private final SpecificationUtil specificationUtil;

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
        OrderService orderService = applicationContextProvider.getBean(OrderService.class);
        Order order = offer.getOrder();

        if (order.getOrderType().equals(OrderType.STARTED)
                || order.getOrderType().equals(OrderType.PAID)
                || order.getOrderType().equals(OrderType.DONE)
                || order.getOrderType().equals(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE)
                )
            throw new CustomExceptionOrderType("order type not valid");


        if (offerRegistrationCheck(offer, offer.getOrder())) {

            repository.save(offer);

            log.debug("debug offer registered {} ", order);


            order.setOrderType(OrderType.WAITING_EXPERT_SELECTION);
            orderService.save(order);

        }
    }

    @Override
    public List<Offer> showOffersByOrder(Order order) {

        return repository.findOfferByOrder_Id(order.getId());

    }

    @Override
    public void chooseOffer(Offer offer, User user) {
        if (!Objects.equals(offer.getOrder().getUser().getId(), user.getId()))
            throw new CustomExceptionInvalid("this offer not for your order");

        OrderService orderService = applicationContextProvider.getBean(OrderService.class);

        Order order = offer.getOrder();

        if (orderService.findById(order.getId())
                .getOffers().stream().allMatch(Offer::isChoose))
            throw new CustomExceptionUpdate("user chosen a offer before it");

        if (!checkLevelWork(offer))
            throw new CustomExceptionUpdate("check level work error");

        if (order.getOrderType().equals(OrderType.WAITING_EXPERT_SELECTION)
                || order.getOrderType().equals(OrderType.WAITING_FOR_THE_SUGGESTIONS)) {


            offer.setChoose(true);
            repository.save(offer);

            order.setOrderType(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE);
            orderService.save(order);
        } else throw new CustomExceptionUpdate("order type invalid");


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
    public List<Offer> findByOrderIdSortedPrice(Long orderId, Long userId) {

        OfferService offerService = applicationContextProvider.getBean(OfferService.class);

        if (Objects.equals(offerService.findById(orderId).getOrder().getUser().getId(), userId))
            return repository.findOfferSortedByPrice(orderId);

        else throw new CustomExceptionInvalid("this order for another user");

    }

    @Override
    public List<Offer> findByOrderIdSortedByPoint(Long orderId, Long userId) {
        OfferService offerService = applicationContextProvider.getBean(OfferService.class);

        if (Objects.equals(offerService.findById(orderId).getOrder().getUser().getId(), userId))
            return repository.findByOrderIdSortedByPoint(orderId);

        else throw new CustomExceptionInvalid("this order for another user");

    }
    @Override
    public int countOfOffers(Long expertId) {
        return repository.countOfOffers(expertId);
    }

    @Override
    public int countOffOrderToDone(Long expertId) {
        return repository.countOffOrderToDone(expertId);
    }

    @Override
    public List<Offer> getAllAcceptedOffersByUserId(Long userId) {
        return repository.getAllAcceptedOffersByUserId(userId);
    }

    @Override
    public List<Offer> getAllDoneOffersByUserId(Long userId) {
        return repository.getAllDoneOffersByUserId(userId);
    }

    @Override
    public List<Offer> findByExpertId(Long expertId) {
        return repository.findOfferByExpertId(expertId);
    }

    @Override
    public List<Offer> offerSpecification(Map<String, String> map) {
        return repository.findAll(specificationUtil.expertSpecification(map));
    }

    @Override
    public Offer findByOrderIdChosen(Long orderId){
        List<Offer> byOrderIdAndChoose = repository.findByOrderIdAndChoose(orderId);
        if (byOrderIdAndChoose.isEmpty())
            throw  new CustomExceptionNotFind("offer not found");
        return byOrderIdAndChoose.get(0);
    }

    private boolean checkLevelWork(Offer offer) {
        Order order = offer.getOrder();
        return order.getId() != null && offer.getId() != null
                && offer.getStartDate().isAfter(LocalDateTime.now());
    }


    private boolean offerRegistrationCheck(Offer offer, Order order) {
        List<ExpertTypeService> expertTypeServiceList = expertTypeServiceService
                .findExpertTypeServiceByExpertId(offer.getExpert().getId());

        if (offer.getEndDate().isAfter(offer.getStartDate())
                && order.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && order.getId() != null
                && offer.getSuggestedPrice() >= order.getTypeService().getBasePrice()
                && !expertTypeServiceList.stream()
                .filter(expertTypeService -> expertTypeService.getTypeService()
                        .equals(order.getTypeService())).toList().isEmpty()) {
            return true;
        } else
            throw new CustomExceptionSave("offer registration not valid");
    }


}

