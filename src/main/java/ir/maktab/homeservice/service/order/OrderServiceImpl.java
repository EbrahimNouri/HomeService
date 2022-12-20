package ir.maktab.homeservice.service.order;


import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.Transaction;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.PaymentType;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.exception.CustomExceptionUpdate;
import ir.maktab.homeservice.exception.CustomNotChoosingException;
import ir.maktab.homeservice.repository.order.OrderRepository;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Log4j2
@Transactional
public class OrderServiceImpl implements OrderService {
    private final OfferService offerService;
    private final ExpertUserService expertUserService;

    private final TransactionService transactionService;

    private OrderRepository repository;

    @Override
    public Optional<Order> findById(Long id){
        return repository.findById(id);
    }

    @Override
    public void save(Order order){
        repository.save(order);
    }

    @Override
    public void orderRegistration(Order order) {
            if (order.getSuggestedPrice() != null
                    && order.getDescription() != null
                    && order.getStartOfWork() != null
                    && order.getUser() != null) {
// TODO: 12/12/2022 AD if 
                order.setOrderType(OrderType.WAITING_FOR_THE_SUGGESTIONS);
                repository.save(order);

                log.debug("debug order registration {} ", order);
            } else {

                log.error("debug order registration the fields are not filled {} ", order);

                throw new CustomExceptionSave("order not valid");
            }

    }

    @Transactional
    @Override
    public void setOrderToDone(Order order) {
        if (orderChecker(order)) {
            // TODO: 12/16/2022 AD   {
            order.setOrderType(OrderType.DONE);
            repository.save(order);
        }else {
            throw new CustomExceptionUpdate("order not valid");
        }
    }


    @Transactional
    @Override
    public void setOrderToPaid(Order order) {
        Offer offer = offerService.findOfferByOrder_Id(order.getId())
                .stream().filter((x) -> x.getOrder().getOrderType().equals(OrderType.DONE)).toList().get(0);

        if (orderChecker(order)
                && order.getOrderType().equals(OrderType.DONE)) {


            if (order.getPaymentType().equals(PaymentType.CREDIT_PAYMENT)) {

                transactionService.addTransaction(Transaction.builder()
                        .expert(offer.getExpert())
                        .user(order.getUser())
                        .transactionType(TransactionType.TRANSFER)
                        .transfer(offer.getSuggestedPrice())
                        .build());

            } else if (order.getPaymentType().equals(PaymentType.ONLINE_PAYMENT)) {

                onlinePayment(order);
                // TODO: 12/17/2022 AD Online payment

            } else
                throw new CustomNotChoosingException("didn't choose any of the payment methods");

            order.setOrderType(OrderType.PAID);


            if (LocalDateTime.now().isAfter(offer.getEndDate())) {

                repository.save(order);

            } else if (offer.getEndDate().isAfter(LocalDateTime.now())) {

                int hour = offer.getEndDate().getHour() - LocalDateTime.now().getHour();
                expertUserService.deductPoints(hour, order);

            }
        }
    }


    public void onlinePayment(Order order) {

    }

    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return repository.findByOrderTypeBeforeStart();
    }

    private boolean orderChecker(Order order) {
        return order.getSuggestedPrice() != null
                && order.getDescription() != null
                && order.getStartOfWork() != null
                && order.getUser() != null
                && order.getId() != null;
    }
}


