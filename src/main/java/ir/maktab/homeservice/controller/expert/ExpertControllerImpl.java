package ir.maktab.homeservice.controller.expert;


import ir.maktab.homeservice.dto.ExpertOffersDto;
import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.dto.OrderDto;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/v1/expert")
public class ExpertControllerImpl {
    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final TypeServiceService typeServiceService;
    private final ExpertUserService expertUserService;

    // TODO: 12/31/2022 AD

    @GetMapping()
    public Person showExpert(Authentication authentication) {
        return (Person) authentication.getPrincipal();

    }

    @PutMapping("/chPass")
    public void changePassword(@RequestParam @Valid String password, Authentication authentication) {
        Person expert = (Person) authentication.getPrincipal();
        expertService.changePassword(expert, password);
    }

    @PostMapping("/offerRegistrationOrUpdate")
    public void offerRegistrationOrUpdate(@RequestBody @Valid OfferDto offerDto, Authentication authentication) {

        Offer offer = Offer.builder()
                .expert((Person) authentication.getPrincipal())
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

    @GetMapping("/getAverageScore")
    public Double getAverageScore(Authentication authentication) {
        Person expert = (Person) authentication.getPrincipal();
        return expert.getAverageScore();
    }

    @GetMapping("/getAllScores")
    public List<Double> getAllScores(Authentication authentication) {
        Person expert = (Person) authentication.getPrincipal();
        return expertUserService.listOfScore(expert.getId());
    }

    @PostMapping("/addAvatar")
    public void addAvatar(Authentication authentication, @RequestBody MultipartFile file) throws IOException {
        Person expert = (Person) authentication.getPrincipal();
        expertService.addAvatar(expert.getId(), file);
    }

    @GetMapping("/showAllOrderList/{typeService}")
    public List<OrderDto> showAllOrderList(@PathVariable Long typeService) {
        TypeService typeServices = typeServiceService.findById(typeService);
        return orderService.findByTypeService(typeServices).stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/getScore")
    public Double getScore(Authentication authentication) {
        Person expert = (Person) authentication.getPrincipal();
        return expertService.getScore(expert.getId());
    }

    @GetMapping("/detail")
    public ExpertOffersDto expertDetail(Authentication authentication) {
        Person expert = (Person) authentication.getPrincipal();
        return
                ExpertOffersDto.expertMappingToExpertOfferDto(expertService.expertDetail(expert.getId()));
    }

    @GetMapping("/myOffers")
    public List<OfferDto> myOffers(Authentication authentication){
        Person principal = (Person) authentication.getPrincipal();
        return offerService.findByExpertId(principal.getId()).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/myPrice")
    public Double myPrice(Authentication authentication){
        Person principal = (Person) authentication.getPrincipal();
        return principal.getCredit();
    }

}
