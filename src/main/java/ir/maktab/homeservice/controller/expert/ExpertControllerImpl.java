package ir.maktab.homeservice.controller.expert;


import ir.maktab.homeservice.dto.ExpertAvatarDto;
import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.Order;
import ir.maktab.homeservice.entity.enums.ExpertStatus;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertUser.ExpertUserService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.List;


@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expert")
public class ExpertControllerImpl implements ExpertController {
    private final ExpertService expertService;
    private final OfferService offerService;
    private final OrderService orderService;
    private final ExpertUserService expertUserService;

    @PostMapping("/regExpert")
    @Override
    public void registerExpert(@RequestBody @Valid PersonRegisterDto personRegisterDto) {
        Expert temp = Expert.builder()
                .expertStatus(ExpertStatus.NEW)
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .credit(0)
                .averageScores(0.0)
                .build();

        expertService.mainRegisterExpert(temp/*,new File("/Users/ebrahimnouri/ss.jpg"*/);


//        Expert expert = Expert.builder().firstname("testName").lastname("testLastname").email("test@email.com")
//                .password("1234QWer").build();
//        expertService.registerExpert(expert, new File("/Users/ebrahimnouri/ss.jpg"));
    }


    @GetMapping("/{id}")
    @Override
    public Expert findById(@PathVariable Long id) {
        return expertService.findById(id);

    }

    @PutMapping("/chPass")
    @Override
    public void changePassword(@RequestBody @Valid PersonChangePasswordDto personChangePasswordDto) {
        Expert expert = expertService.findById(personChangePasswordDto.getId());
        expertService.changePassword(expert, personChangePasswordDto.getPassword());
    }

    @Override
    @PostMapping("/offerRegistrationOrUpdate")
    public void offerRegistrationOrUpdate(OfferDto offerDto) {

        Offer offer = Offer.builder()
                .expert(expertService.findById(offerDto.getExpertId()))
                .order(orderService.findById(offerDto.getOrderId()))
                .description(offerDto.getDescription())
                .startDate(offerDto.getStartDate())
                .EndDate(offerDto.getEndDate())
                .build();

        offerService.offerRegistrationOrUpdate(offer);
    }

    @GetMapping("/showOrderSuggestionOrSelection")
    @Override
    public List<Order> showOrderSuggestionOrSelection() {
        return orderService.showOrderSuggestionOrSelection();
    }

    @Override
    @GetMapping("/getAverageScore/{id}")
    public Double getAverageScore(@PathVariable Long id) {
        return expertService.findById(id).getAverageScore();
    }

    @Override
    @GetMapping("/getAllScores/{id}")
    public List<Double> getAllScores(@PathVariable Long id) {
        return expertUserService.listOfScore(id);
    }

    @Override
    @PostMapping("/addAvatar")
    public void addAvatar(@RequestBody ExpertAvatarDto expertAvatarDto) {
        expertService.addAvatar(expertAvatarDto.getExpertId(), new File(expertAvatarDto.getPath()));
    }
}
