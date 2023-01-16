package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.PaymentType;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/v1/user")
public class UserControllerImpl {
    private final UserService userService;
    private final ExpertService expertService;
    private final OrderService orderService;
    private final ExpertUserService expertUserService;
    private final OfferService offerService;
    private final TypeServiceService typeServiceService;
    private final TransactionService transactionService;


    @PutMapping("/chPass")//checked
    public void changePassword(@Valid @RequestBody String password, Authentication authentication) {
        User temp = (User) authentication.getPrincipal();

        userService.changePassword(temp, password);
    }

    @PostMapping("/addCommentAndPoint")//checked
    public void addCommentAndPoint(@Valid @RequestBody ExpertUserDto expertUserDto, Authentication authentication) {
        ExpertUser expertUser;

        Expert expert = expertService.findById(expertUserDto.getExpId());

        User user = (User) authentication.getPrincipal();

        Order order = orderService.findById(expertUserDto.getOrderId());

        expertUser = getExpertUser(expertUserDto, expert, user, order);

        expertUserService.addCommentAndPoint(expertUser);
    }

    @GetMapping("/showOffersByOrder/{orderId}")//checked
    public List<OfferDto> showOffersByOrder(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId);

        return offerService.showOffersByOrder(order).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @PutMapping("/chooseOffer/{offerId}")//checked
    public void chooseOffer(@PathVariable Long offerId, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        Offer offer = offerService.findById(offerId);

        offerService.chooseOffer(offer, user);
    }

    @PutMapping("/startOfWork/{orderId}")//checked
    public void startOfWork(@PathVariable Long orderId, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        orderService.startOfWork(orderId, user);
    }

    @PutMapping("/endOfTheWork/{orderId}")//checked
    public void endOfTheWork(@PathVariable Long orderId) {

        orderService.endOfTheWork(orderId);
    }

    @PostMapping("/orderRegistration")//checked
    public void orderRegistration(@RequestBody OrderDto orderDto, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        Order order = OrderDto.getOrderForRegister(orderDto, user);

        orderService.orderRegistration(order);
    }


    @PutMapping("/setOrderToDone/{orderId}")//checked
    public void setOrderToDone(@PathVariable Long orderId) {

        orderService.setOrderToDone(orderId);

    }


    @GetMapping("/findByOrderIdSortedPrice/{orderId}")//checked
    public List<OfferDto> findByOrderIdSortedPrice(@PathVariable Long orderId
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return offerService.findByOrderIdSortedPrice(orderId, user.getId()).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/findByOrderIdSortedByPoint/{orderId}")//checked
    public List<OfferDto> findByOrderIdSortedByPoint(@PathVariable Long orderId
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return offerService.findByOrderIdSortedByPoint(orderId, user.getId()).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/showAllTypeService/{id}")//checked
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream()
                .map(TypeServiceDto::typeServiceToTypeServiceDto).toList();
    }

    @GetMapping("findComment/{orderId}") //checked
    public ExpertUser findByOrderId(@PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return expertUserService.findByOrderId(orderId, user.getId());
    }

    @PutMapping("/choosePaymentMethod")//checked
    public void choosePaymentMethod(@RequestParam Long orderId, String paymentType
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        orderService.choosePaymentMethod(orderId,
                PaymentType.valueOf(paymentType.toUpperCase()), user);
    }

    @PutMapping("/setOrderToPaidAppPayment/{orderId}")//checked
    public void setOrderToPaidAppPayment(@PathVariable Long orderId
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        orderService.setOrderToPaidAppPayment(orderId, user);
    }

    @PutMapping("/onlinePayment")//checked
    public void onlinePayment(@RequestParam String card, Long orderId
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        orderService.setOrderToPaidOnlinePayment(orderId, user, card);
    }

    @PutMapping("/chargeAccount")//checked
    public void chargeAccount(@RequestParam String card, double balance
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        transactionService.chargeAccountBalance(user, balance, card);
    }

    @GetMapping("/detail")//checked
    public UserOrderDto userDetail(Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return UserOrderDto.userToUserOrderDtoMapper(userService.userDetail(user));

    }

    @GetMapping("/myOrders")//checked
    public List<OrderDto> myOffers(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return orderService.findByUserId(principal.getId())
                .stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/myPrice")//checked
    public Double myPrice(Authentication authentication) {
        User principal = (User) authentication.getPrincipal();
        return principal.getCredit();
    }

    @GetMapping("/findOrderReqParam")//checked
    public List<OrderDto> findOrderBySpecification(@RequestParam Map<String, String> map, Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        map.put("user", String.valueOf(user.getId()));

        return orderService.findBySpecification(map).stream()
                .map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/getTransactions")//checked
    public List<TransactionDto> getTransActions(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return transactionService.findTransactionByUserId
                (user.getId()).stream().map((TransactionDto::TransactionToDto)).toList();
    }

    @GetMapping("/showMyCommentOrPoint")//checked
    public List<ExpertUserDto> showMyCommentOrPoint(Authentication authentication) {
        User user = (User) authentication.getPrincipal();

        return expertUserService.findByUser(user.getId())
                .stream().map(ExpertUserDto::expertUserDtoMapper).toList();
    }

    private static ExpertUser getExpertUser(ExpertUserDto expertUserDto, Expert expert, User user, Order order) {
        ExpertUser expertUser;
        expertUser = ExpertUser.builder()
                .user(user)
                .expert(expert)
                .order(order)
                .point(expertUserDto.getPoint())
                .comment(expertUserDto.getComment())
                .build();
        return expertUser;
    }
}

