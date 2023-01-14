package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.controller.admin.AdminControllerImpl;
import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    private final AdminControllerImpl adminController;

    @PutMapping("/chPass")
    public void changePassword(@RequestBody String password, Authentication authentication) {
        Person temp = (Person) authentication.getPrincipal();

        userService.changePassword(temp, password);
    }

    @PostMapping("/addCommentAndPoint")
    public void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto, Authentication authentication) {
        ExpertUser expertUser;

        Person expert = expertService.findById(expertUserDto.getExpId());

        Person user = (Person) authentication.getPrincipal();

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

        Person user = (Person) authentication.getPrincipal();
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
        Person user = (Person) authentication.getPrincipal();
        return offerService.findByOrderIdSortedPrice(orderId, user.getId()).stream().map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/findByOrderIdSortedByPoint/{orderId}")
    public List<OfferDto> findByOrderIdSortedByPoint(@PathVariable Long orderId
            , Authentication authentication) {

        Person user = (Person) authentication.getPrincipal();

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
        Person user = (Person) authentication.getPrincipal();
        return expertUserService.findByOrderId(orderId, user.getId());
    }

    @PostMapping("/setOrderToPaidAppPayment/{orderId}")
    public void setOrderToPaidAppPayment(@PathVariable Long orderId, Authentication authentication) {
        Person user = (Person) authentication.getPrincipal();
        orderService.setOrderToPaidAppPayment(orderService.findById(orderId), user);
    }

    @PostMapping("/onlinePayment")
    public void onlinePayment(@RequestBody PaymentOnlineDto paymentOnlineDto) {

        Person user = userService.findByEmail(paymentOnlineDto.getEmail());
        Order order = orderService.findOrderEndWork(user);
        orderService.setOrderToPaidOnlinePayment(order);
        System.out.println("done");
    }

    @PutMapping("/setOrderToPaidAppPayment/{orderId}")
    public void appPayment(@PathVariable Long orderId, Authentication authentication) {
        Order order = orderService.findById(orderId);
        Person user = (Person) authentication.getPrincipal();
        orderService.setOrderToPaidAppPayment(order, user);
    }

    @GetMapping("/detail")
    public UserOrderDto userDetail(Authentication authentication) {
        Person user = (Person) authentication.getPrincipal();
        return UserOrderDto.userToUserOrderDtoMapper(userService.userDetail(user));
    }

    @GetMapping("/myOrders")
    public List<OrderDto> myOffers(Authentication authentication){
        Person principal = (Person) authentication.getPrincipal();
        return orderService.findByUserId(principal.getId())
                .stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/myPrice")
    public Double myPrice(Authentication authentication){
        Person principal = (Person) authentication.getPrincipal();
        return principal.getCredit();
    }

}

