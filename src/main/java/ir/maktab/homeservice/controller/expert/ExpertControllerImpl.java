package ir.maktab.homeservice.controller.expert;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.transaction.TransactionService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("/api/v1/expert")
public class ExpertControllerImpl {
    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final ExpertUserService expertUserService;
    private final ExpertTypeServiceService expertTypeServiceService;
    private final TransactionService transactionService;


    @GetMapping()//checked
    public PersonFindDto showExpert(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return PersonFindDto.expertTopPersonFindDtoMapper(expert);

    }

    @PutMapping("/chPass")//checked
    public void changePassword(@RequestBody @Valid String password, Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        expertService.changePassword(expert, password);
    }

    @PostMapping("/offerRegistrationOrUpdate")//checked
    public void offerRegistrationOrUpdate(@RequestBody @Valid OfferDto offerDto
            , Authentication authentication) {

        Expert expert = (Expert) authentication.getPrincipal();
        Order order = orderService.findById(offerDto.getOrderId());

        offerService.offerRegistrationOrUpdate
                (OfferDto.offerDtoToOfferMapping(offerDto, expert, order));
    }

    @GetMapping("/showOrderSuggestionOrSelection")//checked
    public List<OrderDto> showOrderSuggestionOrSelection() {
        return orderService.showOrderSuggestionOrSelection()
                .stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/showOrderSuggestionOrSelectionForYou")//checked
    public List<OrderDto> findOrderByTypeService(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return orderService.findByTypeService(expert.getExpertTypeServices().get(0).getTypeService())
                .stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/getAverageScore")//checked
    public Double getAverageScore(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return expert.getAverageScore();
    }

    @GetMapping("/getAllScores")//checked
    public List<Double> getAllScores(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return expertUserService.listOfScore(expert.getId());
    }

    //checked
    @PostMapping("/addAvatar")//checked
    public void addAvatar(Authentication authentication, @RequestBody MultipartFile file) throws IOException {
        Expert expert = (Expert) authentication.getPrincipal();
        expertService.addAvatar(expert.getId(), file);
    }

    @GetMapping("/getAvatar")//checked
    public void getAvatar(Authentication authentication, @RequestParam String uri) throws IOException {
        Expert expert = (Expert) authentication.getPrincipal();
        expertService.findById(expert.getId(),
                Path.of(uri));
    }

    @GetMapping("/showAllOrderList") //checked
    public List<OrderDto> showAllOrderList(Authentication authentication) {
        Expert principal = (Expert) authentication.getPrincipal();
        return expertTypeServiceService.findByExpertId(principal.getId())
                .stream().map(OrderDto::orderToOrderDtoMapper).toList();
    }

    @GetMapping("/getScore")//checked
    public Double getScore(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return expertService.getScore(expert.getId());
    }

    @GetMapping("/detail")//checked
    public ExpertOffersDto expertDetail(Authentication authentication) {
        Expert expert = (Expert) authentication.getPrincipal();
        return
                ExpertOffersDto.expertMappingToExpertOfferDto(expertService.expertDetail(expert.getId()));
    }

    @GetMapping("/myOffers")//checked
    public List<OfferDto> myOffers(Authentication authentication) {
        Expert principal = (Expert) authentication.getPrincipal();
        return offerService.findByExpertId(principal.getId()).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/myWallet")//checked
    public Double myPrice(Authentication authentication) {
        Expert principal = (Expert) authentication.getPrincipal();
        return principal.getCredit();
    }

    @GetMapping("/offerInformation")//checked
    public List<OfferDto> offerDtos(@RequestParam Map<String, String> map, Authentication authentication) {

        Expert expert = (Expert) authentication.getPrincipal();
        map.put("expert", String.valueOf(expert.getId()));

        return offerService.offerSpecification(map).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @GetMapping("/getTransactions")//checked
    public List<TransactionDto> getTransActions(Authentication authentication) {

        Expert expert = (Expert) authentication.getPrincipal();

        return transactionService.findTransactionByExpertId
                (expert.getId()).stream().map((TransactionDto::TransactionToDto)).toList();
    }
}
