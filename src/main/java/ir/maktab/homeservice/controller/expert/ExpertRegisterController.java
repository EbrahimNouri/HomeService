package ir.maktab.homeservice.controller.expert;

import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.service.expert.ExpertService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping
public class ExpertRegisterController {

    private final ExpertService expertService;


    @PostMapping("/expert")
    public String registerExpert(@RequestBody @Validated PersonRegisterDto personRegisterDto)
            throws MessagingException, UnsupportedEncodingException {

        Expert temp = PersonRegisterDto.personDtoExpertMapping(personRegisterDto);

        expertService.register(temp, "http://localhost:8099/api/v1/register/verifyExpert/");

        return "register_success";
    }

    @GetMapping("verifyExpert/{code}")
    public String verifyExpert(@PathVariable Integer code) {
        if (expertService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }
}
