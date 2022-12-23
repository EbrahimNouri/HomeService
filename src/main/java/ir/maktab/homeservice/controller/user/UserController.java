package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.User;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface UserController {
    void registerUser(PersonRegisterDto personRegisterDto);

    void changePassword(PersonChangePasswordDto personChangePasswordDto);

    User findById(Long id);

    @PostMapping("/addCommentAndPoint")
    void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto);

    @GetMapping("/showOffersByOrder/{orderId}")
    List<Offer> showOffersByOrder(@PathVariable Long orderId);

    @PutMapping("/chooseOffer/{offerId}")
    void chooseOffer(@PathVariable Long offerId);

    @PutMapping("/startOfWork/{offerId}")
    void startOfWork(@PathVariable Long offerId);

    @PutMapping("/endOfTheWork/{offerId}")
    void endOfTheWork(@PathVariable Long offerId);

    @PostMapping("/orderRegistration")
    void orderRegistration(@RequestBody OrderDto orderDto);

    @PutMapping("/setOrderToDone/{orderId}")
    void setOrderToDone(@PathVariable Long orderId);

    @GetMapping("/findByOrderIdSortedPrice/{orderId}")
    List<Offer> findByOrderIdSortedPrice(@PathVariable Long orderId);

    @PostMapping("/addTransaction")
    void addTransaction(@RequestBody TransactionDto transactionDto);

    @GetMapping("/showAllTypeService/{id}")
    List<TypeService> findByBasicServiceId(@PathVariable Long id);

    @GetMapping("/{orderId}")
    ResponseEntity<ExpertUser> findByOrderId(@PathVariable Long orderId);
}
