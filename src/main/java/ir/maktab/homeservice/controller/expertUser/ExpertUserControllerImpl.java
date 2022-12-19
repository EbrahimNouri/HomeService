package ir.maktab.homeservice.controller.expertUser;


import ir.maktab.homeservice.dto.ExpertUserDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertUser;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.entity.enums.OrderType;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expertUser")
public class ExpertUserControllerImpl implements ExpertUserController {

    private final ExpertUserRepository expertUserRepository;
    private final ExpertRepository expertRepository;
    private ExpertUserService service;
    private UserService userService;
    private OrderService orderService;

    @PostMapping("/addCommentAndPoint")
    @Override
    public void addCommentAndPoint(@RequestBody ExpertUserDto expertUserDto) {
        ExpertUser expertUser = new ExpertUser();

        Expert expert = expertRepository.findById(expertUserDto.getExpId())
                .orElseThrow(() -> new CustomExceptionNotFind("user not found"));

        User user = userService.findById(expertUserDto.getUserId())
                .orElseThrow(() -> new CustomExceptionNotFind("user not found"));

        Order order = orderService.findById(expertUserDto.getOrderId())
                .orElseThrow(() -> new CustomExceptionNotFind("user not found"));
        // TODO: 12/17/2022 AD this is for test ⬇︎
        order.setOrderType(OrderType.DONE);

        expertUser = ExpertUser.builder()
                .user(user)
                .expert(expert)
                .order(order)
                .point(expertUserDto.getPoint())
                .comment(expertUserDto.getComment())
                .build();

        service.addCommentAndPoint(expertUser);
    }

    @GetMapping("/{orderId}")
    @Override
    public ResponseEntity<ExpertUser> findByOrderId(@PathVariable Long orderId) {
        return service.findByOrderId(orderId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}