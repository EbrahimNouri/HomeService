package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.controller.admin.AdminControllerImpl;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.Authentication;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserControllerImpl {

    private final UserService userService;
    private final ExpertService expertService;
    private final OrderService orderService;
    private final ExpertUserService expertUserService;
    private final OfferService offerService;
    private final TypeServiceService typeServiceService;
    private final AdminControllerImpl adminController;

    @Secured("permitAll")
    @PostMapping("/register")
    public void registerUser(@RequestBody @Validated PersonRegisterDto personRegisterDto)
            throws MessagingException, UnsupportedEncodingException {

        User temp = User.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .username(personRegisterDto.getUsername())
                .password(personRegisterDto.getPassword())
                .build();

        userService.register(temp, "/api/v1/user");
    }

    @GetMapping("/verify")
    public String verifyUser(@Param("code") Integer code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PutMapping("/chPass")
    public void changePassword(@RequestBody String password, Authentication authentication) {
        User temp = (User) authentication.getPrincipal();

        userService.changePassword(temp, password);
    }

    @PostMapping("/addCommentAndPoint")
    public void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto, Authentication authentication) {
        ExpertUser expertUser;

        Expert expert = expertService.findById(expertUserDto.getExpId());

        User user = (User) authentication.getPrincipal();

        Order order = orderService.findById(expertUserDto.getOrderId());

        expertUser = ExpertUser.builder()
                .user(user)
                .expert(expert)
                .order(order)
                .point(expertUserDto.getPoint())
                .comment(expertUserDto.getComment())
                .build();

        expertUserService.addCommentAndPoint(expertUser);
    }

    @GetMapping("/showOffersByOrder/{orderId}")
    public List<OfferDto> showOffersByOrder(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId);

        return offerService.showOffersByOrder(order).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @PutMapping("/chooseOffer/{offerId}")
    public void chooseOffer(@PathVariable Long offerId) {
        Offer offer = offerService.findById(offerId);

        offerService.chooseOffer(offer);
    }

    @PutMapping("/startOfWork/{orderId}")
    public void startOfWork(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);

        orderService.startOfWork(order);
    }

    @PutMapping("/endOfTheWork/{orderId}")
    public void endOfTheWork(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);

        orderService.endOfTheWork(order);
    }

    @PostMapping("/orderRegistration")
    public void orderRegistration(@RequestBody OrderDto orderDto, Authentication authentication) {

        User user = (User) authentication.getPrincipal();
        TypeService typeService = typeServiceService.findById(orderDto.getTypeId());
        Order order = Order.builder()
                .address(orderDto.getAddress())
                .description(orderDto.getDescription())
                .user(user)
                .typeService(typeService)
                .startOfWork(orderDto.getStartOfWork())
                .suggestedPrice(orderDto.getPrice())
                .build();

        orderService.orderRegistration(order);
    }

    @PutMapping("/setOrderToDone/{orderId}")
    public void setOrderToDone(@PathVariable Long orderId) {
        Order order = orderService.findById(orderId);
        orderService.setOrderToDone(order);
    }


    @GetMapping("/findByOrderIdSortedPrice/{orderId}")
    public List<OfferDto> findByOrderIdSortedPrice(@PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return offerService.findByOrderIdSortedPrice(orderId, user.getId()).stream().map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/findByOrderIdSortedByPoint/{orderId}")
    public List<OfferDto> findByOrderIdSortedByPoint(@PathVariable Long orderId
            , Authentication authentication) {

        User user = (User) authentication.getPrincipal();

        return offerService.findByOrderIdSortedByPoint(orderId, user.getId()).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream()
                .map(adminController::typeServiceMapped).toList();
    }

    @GetMapping("/{orderId}")
    public ExpertUser findByOrderId(@PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return expertUserService.findByOrderId(orderId, user.getId());
    }

    @PostMapping("/setOrderToPaidAppPayment/{orderId}")
    public void setOrderToPaidAppPayment(@PathVariable Long orderId, Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        orderService.setOrderToPaidAppPayment(orderService.findById(orderId), user);
    }

    @PostMapping("/onlinePayment")
    public void onlinePayment(@RequestBody PaymentOnlineDto paymentOnlineDto) {

        User user = userService.findByEmail(paymentOnlineDto.getEmail());
        Order order = orderService.findOrderEndWork(user);
        orderService.setOrderToPaidOnlinePayment(order);
        System.out.println("done");
    }

    @PutMapping("/setOrderToPaidAppPayment/{orderId}")
    public void appPayment(@PathVariable Long orderId, Authentication authentication) {
        Order order = orderService.findById(orderId);
        User user = (User) authentication.getPrincipal();
        orderService.setOrderToPaidAppPayment(order, user);
    }

    @GetMapping("/detail")
    public UserOrderDto userDetail(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return UserOrderDto.userToUserOrderDtoMapper(userService.userDetail(user));
    }



}

