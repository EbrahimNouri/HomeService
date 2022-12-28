package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {

    private final ExpertUserService expertUserService;
    private final TransactionService transactionService;

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
// TODO: 12/28/2022 AD for test commented 

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
    public void setOrderToPaid(Order order) {

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

        } else if (order.getPaymentType().equals(PaymentType.ONLINE_PAYMENT)) {

            onlinePayment(offer);
            // TODO: 12/17/2022 AD Online payment

        } else
            throw new CustomNotChoosingException("didn't choose any of the payment methods");

        order.setOrderType(OrderType.PAID);

        timeChecker(order, offer);
    }

    private void timeChecker(Order order, Offer offer) {
        if (LocalDateTime.now().isAfter(offer.getEndDate())) {

            repository.save(order);

        } else if (offer.getEndDate().isAfter(LocalDateTime.now())) {

            int hour = offer.getEndDate().getHour() - LocalDateTime.now().getHour();
            expertUserService.deductPoints(hour, order);

        }
    }


    public void onlinePayment(Offer offer) {
        transactionService.onlinePayment(offer.getExpert(), offer.getSuggestedPrice());
    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {

        return repository.findByOrderTypeBeforeStart();

    }

    @Override
    public List<Order> findByTypeService(TypeService typeService) {
        return repository.findByTypeService(typeService);
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
