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
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/chPass")
    public void changePassword(@RequestBody PersonChangePasswordDto personChangePasswordDto) {
        User temp = userService.findById(personChangePasswordDto.getId());

        userService.changePassword(temp, personChangePasswordDto.getPassword());
    }

    @PostMapping("/addCommentAndPoint")
    public void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto) {
        ExpertUser expertUser;

        Expert expert = expertService.findById(expertUserDto.getExpId());

        User user = userService.findById(expertUserDto.getUserId());

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
                .map(this::offerMapping).toList();
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
    public void orderRegistration(@RequestBody OrderDto orderDto) {

        User user = userService.findById(orderDto.getUserId());
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
    public List<OfferDto> findByOrderIdSortedPrice(@PathVariable Long orderId) {
        return offerService.findByOrderIdSortedPrice(orderId).stream().map(this::offerMapping).toList();
    }

    @GetMapping("/findByOrderIdSortedByPoint/{orderId}")
    public List<OfferDto> findByOrderIdSortedByPoint(@PathVariable Long orderId) {
        return offerService.findByOrderIdSortedByPoint(orderId).stream().map(this::offerMapping).toList();
    }

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream().map(adminController::typeServiceMapped).toList();
    }

    @GetMapping("/{orderId}")
    public ExpertUser findByOrderId(@PathVariable Long orderId) {
        return expertUserService.findByOrderId(orderId);
    }

    @PostMapping("/setOrderToPaidAppPayment/{orderId}")
    public void setOrderToPaidAppPayment(@PathVariable Long orderId) {
        orderService.setOrderToPaidAppPayment(orderService.findById(orderId));
    }

    @PostMapping("/onlinePayment")
    public void onlinePayment(@RequestBody PaymentOnlineDto paymentOnlineDto) {

        User user = userService.findByEmail(paymentOnlineDto.getEmail());
        Order order = orderService.findOrderEndWork(user);
        orderService.setOrderToPaidOnlinePayment(order);
        System.out.println("done");
    }

    @PutMapping("/setOrderToPaidAppPayment/{orderId}")
    public void appPayment(@PathVariable Long orderId) {

        Order order = orderService.findById(orderId);
        orderService.setOrderToPaidAppPayment(order);
    }


    private OfferDto offerMapping(Offer offer) {
        return OfferDto.builder()
                .orderId(offer.getOrder().getId())
                .expertId(offer.getExpert().getId())
                .startDate(offer.getStartDate())
                .suggestedPrice(offer.getSuggestedPrice())
                .endDate(offer.getEndDate())
                .description(offer.getDescription())
                .suggestedPrice(offer.getSuggestedPrice())
                .build();
    }

    private personFindDto userMapper(User user) {
        return personFindDto.builder()
                .id(user.getId())
                .signupDate(user.getSignupDateTime())
                .personType(PersonType.USER)
                .email(user.getEmail())
                .credit(user.getCredit())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .build();
    }
}

