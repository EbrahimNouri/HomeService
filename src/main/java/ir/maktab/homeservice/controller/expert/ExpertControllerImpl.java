package ir.maktab.homeservice.controller.expert;


import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.dto.OrderDto;
import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.access.annotation.Secured;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expert")
public class ExpertControllerImpl {
    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final TypeServiceService typeServiceService;
    private final ExpertUserService expertUserService;

    // TODO: 12/31/2022 AD
    @Secured("permitAll")
    @PostMapping("/register")
    public void registerExpert(@RequestBody @Validated PersonRegisterDto personRegisterDto) {
        Expert temp = Expert.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .username(personRegisterDto.getUsername())
                .build();

        expertService.mainRegisterExpert(temp);
    }


    @GetMapping("/{id}")
    public Expert findById(@PathVariable Long id) {
        return expertService.findById(id);

    }

    @PutMapping("/chPass")
    public void changePassword(@RequestBody @Valid PersonChangePasswordDto personChangePasswordDto) {
        Expert expert = expertService.findById(personChangePasswordDto.getId());
        expertService.changePassword(expert, personChangePasswordDto.getPassword());
    }

    @GetMapping("findExpertById/{id}")
    public String findExpertById(@PathVariable Long id) {
        Expert expert = expertService.findById(id);
        System.out.println(expert.getEmail());
        return "ok";
    }

    @PostMapping("/offerRegistrationOrUpdate")
    public void offerRegistrationOrUpdate(@RequestBody @Valid OfferDto offerDto) {

        Offer offer = Offer.builder()
                .expert(expertService.findById(offerDto.getExpertId()))
                .order(orderService.findById(offerDto.getOrderId()))
                .description(offerDto.getDescription())
                .startDate(offerDto.getStartDate())
                .EndDate(offerDto.getEndDate())
                .suggestedPrice(offerDto.getSuggestedPrice())
                .build();

        offerService.offerRegistrationOrUpdate(offer);
    }

    @GetMapping("/showOrderSuggestionOrSelection")
    public List<Order> showOrderSuggestionOrSelection() {
        return orderService.showOrderSuggestionOrSelection();
    }

    @GetMapping("/getAverageScore/{id}")
    public Double getAverageScore(@PathVariable Long id) {
        return expertService.findById(id).getAverageScore();
    }

    @GetMapping("/getAllScores/{id}")
    public List<Double> getAllScores(@PathVariable Long id) {
        return expertUserService.listOfScore(id);
    }

    @PostMapping("/addAvatar/{expertId}")
    public void addAvatar(@PathVariable Long expertId, @RequestBody MultipartFile file) throws IOException {
        expertService.addAvatar(expertId,file);
    }

    @GetMapping("/showAllOrderList/{typeService}")
    public List<OrderDto> showAllOrderList(@PathVariable Long typeService) {
        TypeService typeServices = typeServiceService.findById(typeService);
        return orderService.findByTypeService(typeServices).stream().map(this::orderMapping).toList();
    }

    @GetMapping("/getScore/{expertId}")
    public Double getScore(@PathVariable Long expertId){
        return expertService.getScore(expertId);
    }

    public OrderDto orderMapping(Order order) {
        return OrderDto.builder()
                .userId(order.getUser().getId())
                .address(order.getAddress())
                .price(order.getSuggestedPrice())
                .startOfWork(order.getStartOfWork())
                .description(order.getDescription())
                .build();
    }

}
