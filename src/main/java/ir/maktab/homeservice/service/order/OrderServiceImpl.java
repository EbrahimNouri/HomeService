package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import ir.maktab.homeservice.util.SpecificationUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {
    private final BasicServiceRepository basicServiceRepository;

    private final ExpertUserService expertUserService;
    private final TransactionService transactionService;
    private final SpecificationUtil specificationUtil;

    private OrderRepository repository;
    private OfferService offerService;


    @Override
    public Order findById(Long id) {
        return repository.findById(id).orElseThrow(() -> new CustomExceptionNotFind("order not found"));
    }

    @Override
    public void save(Order order) {
        repository.save(order);
    }

    @Override
    public void orderRegistration(Order order) {

        if (order.getSuggestedPrice() == null
                || order.getDescription() == null
                || order.getStartOfWork() == null
                || order.getUser() == null)
            throw new CustomExceptionSave("order not valid");

        if (order.getTypeService().getBasePrice() > order.getSuggestedPrice())
            throw new CustomExceptionSave("order price fewer base price");

        order.setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
        repository.save(order);

        log.debug("debug order registration {} ", order);

    }

    @Override
    public void startOfWork(Order order) {

        Offer offer = getOffer(order);
        if (checkLevelWork(offer))
            throw new CustomExceptionUpdate("this order not valid");

        if (!order.getOrderType().equals(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE))
            throw new CustomExceptionUpdate("order type not invalid");

        if (offer.getStartDate().isBefore(LocalDateTime.now()))
            throw new CustomExceptionUpdate("start of work is after offer set");

        order.setOrderType(OrderType.STARTED);
        repository.save(order);


        log.debug("debug start of work {} ", order);


    }


    @Override
    public void endOfTheWork(Order order) {
        Offer offer = getOffer(order);

        if (checkLevelWork(offer))
            throw new CustomExceptionUpdate("order check level work error");

        if (!order.getOrderType().equals(OrderType.STARTED))
            throw new CustomExceptionUpdate("this order not valid");

        if (LocalDateTime.now().isAfter(offer.getEndDate())) {

            order.setDelayEndWorkHours(ChronoUnit.HOURS.between(LocalDateTime.now(), offer.getEndDate()));
            repository.save(order);

            log.debug("debug done of work {} ", order);
        }

        order.setOrderType(OrderType.DONE);
        repository.save(order);


    }

    @Transactional
    @Override
    public void setOrderToDone(Order order) {

        if (orderChecker(order))
            throw new CustomExceptionUpdate("order have empty variable");

        if (!order.getOrderType().equals(OrderType.STARTED))
            throw new CustomExceptionOrderType("order not valid");

        order.setOrderType(OrderType.DONE);
        repository.save(order);
    }

    @Transactional
    @Override
    public void setOrderToPaidAppPayment(Order order, User user) {
        if (Objects.equals(order.getUser().getId(), user.getId())) {

            Offer offer = offerService.findOfferByOrder_Id(order.getId())
                    .stream().filter(Offer::isChoose).findFirst()
                    .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));


            if (orderChecker(order)
                    && !order.getOrderType().equals(OrderType.DONE))
                throw new CustomExceptionOrderType("order type is invalid");

            if (order.getPaymentType().equals(PaymentType.CREDIT_PAYMENT)) {

                transactionService.addTransaction(Transaction.builder()
                        .expert(offer.getExpert())
                        .user(order.getUser())
                        .transactionType(TransactionType.TRANSFER)
                        .transfer(offer.getSuggestedPrice())
                        .build());

            } else
                throw new CustomNotChoosingException("didn't choose any of the payment methods");

            order.setOrderType(OrderType.PAID);

            timeChecker(order, offer);
        }
    }

    @Transactional
    @Override
    public void setOrderToPaidOnlinePayment(Order order) {

        Offer offer = offerService.findOfferByOrder_Id(order.getId())
                .stream().filter(Offer::isChoose).findFirst()
                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));


        if (orderChecker(order)
                && !order.getOrderType().equals(OrderType.DONE))
            throw new CustomExceptionOrderType("order type is invalid");

        if (order.getPaymentType().equals(PaymentType.ONLINE_PAYMENT)) {

            onlinePayment(offer);

        } else
            throw new CustomNotChoosingException("didn't choose any of the payment methods");

        order.setOrderType(OrderType.PAID);

        timeChecker(order, offer);
    }

    // TODO: 1/4/2023 AD test it
    public List<Order> findBySpecification(Map<String, String> find) {
        List<Order> orders = new ArrayList<>();
        // "from" date "to" date
        if (find.containsKey("from") || find.containsKey("to")) {
            orders.addAll(repository.findOrdersByPriodTime
                    (LocalDate.parse(find.get("from")), LocalDate.parse(find.get("to"))));

            find.remove("from");
            find.remove("to");
        }

        if (find.containsKey("basicService")) {
            orders.addAll(repository.findByBasicServiceName(find.get("basicService")));
            find.remove("basicService");
        }

        if (find.containsKey("subService")) {
            orders.addAll(repository.findOrderByTypeServiceName(find.get("subService")));
            find.remove("subService");
        }

        orders.addAll(repository.findAll(specificationUtil.mapToSpecification(find)));

        return orders;
    }

    private void timeChecker(Order order, Offer offer) {
        if (LocalDateTime.now().isBefore(offer.getEndDate())) {

            repository.save(order);

        } else if (offer.getEndDate().isBefore(LocalDateTime.now())) {

            int hour = offer.getEndDate().getHour() - LocalDateTime.now().getHour();

            expertUserService.deductPoints(hour, order);
        }
    }


    public void onlinePayment(Offer offer) {
        transactionService.onlinePayment(offer.getExpert(), offer.getSuggestedPrice());
    }

    @Override
    public Order findOrderEndWork(User user) {
        return repository.findOrderByUserAndOrderType(user, OrderType.DONE)
                .orElseThrow(() -> new CustomExceptionNotFind("order not found!"));
    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {

        return repository.findByOrderTypeBeforeStart();

    }

    @Override
    public List<Order> findByTypeService(TypeService typeService) {
        return repository.findOrderByTypeService(typeService);
    }

    @Override
    public int countOfOrdersByUserId(Long userId){
        return repository.countOfOrdersByUserId(userId);
    }


    private boolean orderChecker(Order order) {
        return
                order.getSuggestedPrice() == null
                        || order.getDescription() == null
                        || order.getStartOfWork() == null
                        || order.getUser() == null
                        || order.getId() == null;

    }

    private boolean checkLevelWork(Offer offer) {
        Order order = offer.getOrder();
        return order.getId() != null && offer.getId() != null
                && offer.getStartDate().isAfter(LocalDateTime.now());
    }

    private Offer getOffer(Order order) {
        List<Offer> offers = offerService.findByOrder(order).stream().filter(Offer::isChoose).toList();
        if (offers.isEmpty()) {
            throw new CustomExceptionNotFind("offer chosen is empty");
        } else return offers.get(0);
    }
}
