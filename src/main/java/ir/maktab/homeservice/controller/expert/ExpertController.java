package ir.maktab.homeservice.controller.expert;

import ir.maktab.homeservice.dto.ExpertAvatarDto;
import ir.maktab.homeservice.dto.OfferDto;
import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.Order;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface ExpertController {


    @PostMapping("/mainRegExpert")
    void registerExpert(@RequestBody @Valid PersonRegisterDto personRegisterDto);

    @GetMapping("/{id}")
    Expert findById(@PathVariable Long id);

    @PutMapping("/chPass")
    void changePassword(@RequestBody PersonChangePasswordDto personChangePasswordDto);

    @PostMapping("/offerRegistrationOrUpdate")
    void offerRegistrationOrUpdate(OfferDto offerDto);

    @GetMapping("/showOrderSuggestionOrSelection")
    List<Order> showOrderSuggestionOrSelection();

    @GetMapping("/getAverageScore/{id}")
    public Double getAverageScore(@PathVariable Long id);

    @GetMapping("/getAllScores/{id}")
    List<Double> getAllScores(@PathVariable Long id);

    @PostMapping("/addAvatar/{expert}")
    void addAvatar(@RequestBody ExpertAvatarDto expertAvatarDto);
}
