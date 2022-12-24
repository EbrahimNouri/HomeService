package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.entity.enums.TransactionType;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserControllerImpl implements UserController {

    private final UserService userService;
    private final ExpertService expertService;
    private final OrderService orderService;
    private final ExpertUserService expertUserService;
    private final OfferService offerService;
    private final TransactionService transactionService;
    private final TypeServiceService typeServiceService;

    @Override
    @PostMapping("/add")
    public void registerUser(@RequestBody PersonRegisterDto personRegisterDto) {
        User temp = User.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .credit(0)
                .build();

        userService.mainRegisterUser(temp);
    }

    @Override
    @PutMapping("/chPass")
    public void changePassword(@RequestBody PersonChangePasswordDto personChangePasswordDto) {
        User temp = userService.findById(personChangePasswordDto.getId());

        userService.changePassword(temp, personChangePasswordDto.getPassword());
    }

    @Override
    @GetMapping("{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @PostMapping("/addCommentAndPoint")
    @Override
    public void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto) {
        ExpertUser expertUser = new ExpertUser();

        Expert expert = expertService.findById(expertUserDto.getExpId());

        User user = userService.findById(expertUserDto.getUserId());

        Order order = orderService.findById(expertUserDto.getOrderId());
        // TODO: 12/17/2022 AD this is for test ⬇︎
        order.setOrderType(OrderType.PAID);

        expertUser = ExpertUser.builder()
                .user(user)
                .expert(expert)
                .order(order)
                .point(expertUserDto.getPoint())
                .comment(expertUserDto.getComment())
                .build();

        expertUserService.addCommentAndPoint(expertUser);
    }

    @Override
    @GetMapping("/showOffersByOrder/{orderId}")
    public List<Offer> showOffersByOrder(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId);

        return offerService.showOffersByOrder(order);
    }

    @Override
    @PutMapping("/chooseOffer/{offerId}")
    public void chooseOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);

        offerService.chooseOffer(offer);
    }

    @Override
    @PutMapping("/startOfWork/{offerId}")
    public void startOfWork(@PathVariable Long offerId) {
//        Offer offer = service.findById(offerId)
//                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));
//
//        service.startOfWork(offer);
    }

    @Override
    @PutMapping("/endOfTheWork/{offerId}")
    public void endOfTheWork(@PathVariable Long offerId) {
//        Offer offer = service.findById(offerId)
//                .orElseThrow(() -> new CustomExceptionNotFind("offer not found"));
//
//        service.endOfTheWork(offer);
    }

    @Override
    @PostMapping("/orderRegistration")
    public void orderRegistration(@RequestBody OrderDto orderDto) {

        Order order = Order.builder()
                .orderType(OrderType.WAITING_FOR_THE_SUGGESTIONS)
                .address(orderDto.getAddress())
                .description(orderDto.getDescription())
                .user(userService.findById(orderDto.getUserId()))
                .startOfWork(orderDto.getStartOfWork())
                .suggestedPrice(orderDto.getPrice())
                .build();

        orderService.orderRegistration(order);
    }

    @PutMapping("/setOrderToDone/{orderId}")
    @Override
    public void setOrderToDone(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        orderService.setOrderToDone(order);
    }


    @Override
    @GetMapping("/findByOrderIdSortedPrice/{orderId}")
    public List<Offer> findByOrderIdSortedPrice(@PathVariable Long orderId) {
        return offerService.findByOrderIdSortedPrice(orderId);
    }

    // TODO: 12/19/2022 AD find by findByOrderIdSortedPoint


    @PostMapping("/addTransaction")
    @Override
    public void addTransaction(@RequestBody TransactionDto transactionDto) {
        Transaction transaction = Transaction.builder()
                .transactionType(TransactionType.TRANSFER)
                .expert(expertService.findById(transactionDto.getExpertId()))
                .user(userService.findById(transactionDto.getUserid()))
                .build();

        transactionService.addTransaction(transaction);
    }

    @Override
    @GetMapping("/showAllTypeService/{id}")
    public List<TypeService> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id);
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ExpertUser> findByOrderId(@PathVariable Long orderId) {
        return expertUserService.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}
