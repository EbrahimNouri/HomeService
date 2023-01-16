package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.controller.user.CaptchaController;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.*;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.util.SpecificationUtil;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

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
    private final ExpertTypeServiceRepository expertTypeServiceRepository;
    private final BasicServiceRepository basicServiceRepository;

    private final ExpertUserService expertUserService;
    private final TransactionService transactionService;
    private final SpecificationUtil specificationUtil;

    private OrderRepository repository;
    private OfferService offerService;
    private final TypeServiceService typeServiceService;


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

        order.setTypeService(typeServiceService.findById(order.getTypeService().getId()));

        if (order.getSuggestedPrice() == null
                || order.getDescription() == null
                || order.getStartOfWork() == null
                || order.getUser() == null)
            throw new CustomExceptionSave("order not valid");

        if (order.getTypeService().getBasePrice() > order.getSuggestedPrice())
            throw new CustomExceptionSave("order price fewer base price");

        order.setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
        repository.save(order);
    }

    @Override
    public void startOfWork(Long orderId, User user) {

        Order order = findById(orderId);

        if (!order.getUser().equals(user))
            throw new CustomExceptionInvalid("order and user not equal");

        Offer offer = getOffer(order);
        if (checkLevelWork(offer))
            throw new CustomExceptionUpdate("offer date time not valid");

        if (!order.getOrderType().equals(OrderType.WAITING_FOR_COME_TO_YOUR_PLACE))
            throw new CustomExceptionUpdate("order type not WAITING_FOR_COME_TO_YOUR_PLACE");

        if (offer.getStartDate().isBefore(LocalDateTime.now()))
            throw new CustomExceptionUpdate("start of work is after offer set");

        order.setOrderType(OrderType.STARTED);
        repository.save(order);


        log.debug("debug start of work {} ", order);


    }


    @Override
    public void endOfTheWork(Long orderId) {
        Order order = findById(orderId);
        Offer offer = getOffer(order);

        if (checkLevelWork(offer))
            throw new CustomExceptionUpdate("order check level work error");

        if (!order.getOrderType().equals(OrderType.STARTED))
            throw new CustomExceptionUpdate("this order not started");

        if (LocalDateTime.now().isAfter(offer.getEndDate())) {

            order.setDelayEndWorkHours(ChronoUnit.HOURS.between(LocalDateTime.now(), offer.getEndDate()));
            repository.save(order);

        }

        order.setOrderType(OrderType.DONE);
        repository.save(order);


    }

    @Transactional
    @Override
    public void setOrderToDone(Long orderId) {

        Order order = findById(orderId);

        if (orderChecker(order))
            throw new CustomExceptionUpdate("order have empty variable");

        if (!order.getOrderType().equals(OrderType.STARTED))
            throw new CustomExceptionOrderType("order not started");

        order.setOrderType(OrderType.DONE);
        repository.save(order);
    }

    @Override
    public void choosePaymentMethod(Long orderId, PaymentType paymentType, User user) {
        Order order = findById(orderId);
        if (!order.getUser().equals(user))
            throw new CustomExceptionInvalid("user with this order invalid");

        if (!order.getOrderType().equals(OrderType.DONE))
            throw new CustomExceptionInvalid("order type must be done");
        order.setPaymentType(paymentType);
        repository.save(order);
    }

    @Transactional
    @Override
    public void setOrderToPaidAppPayment(Long orderId, User user) {
        Order order = findById(orderId);

        if (!Objects.equals(order.getUser().getId(), user.getId()))
            throw new CustomExceptionInvalid("order and user not valid");

        Offer offer = offerService.findOfferByOrder_Id(order.getId())
                .stream().filter(Offer::isChoose).findFirst()
                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));


        if (orderChecker(order)
                && !order.getOrderType().equals(OrderType.DONE))
            throw new CustomExceptionOrderType("order type is invalid");

        if (!order.getPaymentType().equals(PaymentType.CREDIT_PAYMENT))
            throw new CustomNotChoosingException("didn't choose any of the payment methods");


        transactionService.addTransaction(Transaction.builder()
                .expert(offer.getExpert())
                .user(order.getUser())
                .transactionType(TransactionType.TRANSFER)
                .transfer(offer.getSuggestedPrice())
                .build());


        order.setOrderType(OrderType.PAID);

        timeChecker(order, offer);
    }

    @Transactional
    @Override
    public void setOrderToPaidOnlinePayment(Long orderId, User user, String card) {

        if (CaptchaController.cardCheck(card))
            throw new CustomExceptionInvalid("card is invalid");

        Order order = findById(orderId);

        if (!user.equals(order.getUser()))
            throw new CustomExceptionInvalid("user and order is invalid");

        Offer offer = offerService.findByOrderIdChosen(order.getId());


        if (orderChecker(order)
                && !order.getOrderType().equals(OrderType.DONE))
            throw new CustomExceptionOrderType("order type isn't done");

        if (!order.getPaymentType().equals(PaymentType.ONLINE_PAYMENT))
            throw new CustomNotChoosingException("didn't choose any of the payment methods");

        transactionService.onlinePayment(offer.getOrder().getUser(), offer.getExpert(), offer.getSuggestedPrice());

        order.setOrderType(OrderType.PAID);

        timeChecker(order, offer);
    }

    private void timeChecker(Order order, Offer offer) {
        if (LocalDateTime.now().isBefore(offer.getEndDate())) {

            repository.save(order);

        } else if (offer.getEndDate().isBefore(LocalDateTime.now())) {

            int hour = offer.getEndDate().getHour() - LocalDateTime.now().getHour();

            expertUserService.deductPoints(hour, order);
        }
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
    public int countOfOrdersByUserId(Long userId) {
        return repository.countOfOrdersByUserId(userId);
    }

    @Override
    public List<Order> findByUserId(Long id) {
        return repository.findByUserId(id);
    }

    @Override
    public List<Order> findBySpecification(Map<String, String> map) {
        return repository.findAll(specificationUtil.OrderSpecification(map));
    }

    @Override
    public List<Order> findOrdersByTypeServices(List<TypeService> typeServices) {
        List<Order> orders = new ArrayList<>();
        System.out.println("debug12");

        for (TypeService ts : typeServices) {
            orders.addAll(repository.findOrdersByTypeServices(ts.getId()));
            System.out.println("debug");
        }
        return orders;
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
                && offer.getStartDate().isBefore(LocalDateTime.now());
    }

    private Offer getOffer(Order order) {
        return offerService.findByOrder(order).stream()
                .filter(Offer::isChoose).findFirst().orElseThrow(
                        () -> new CustomExceptionNotFind("offer chosen is empty"));

    }
}
