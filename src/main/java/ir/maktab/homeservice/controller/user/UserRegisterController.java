package ir.maktab.homeservice.controller.user;

import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.mail.MessagingException;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@AllArgsConstructor
@RequestMapping("api/v1/register")
public class UserRegisterController {

    private final UserService userService;


    @PostMapping("/user")
    public String registerExpert(@RequestBody @Validated PersonRegisterDto personRegisterDto)
            throws MessagingException, UnsupportedEncodingException {

        User temp = PersonRegisterDto.personDtoUserMapping(personRegisterDto);

        userService.register(temp, "http://localhost:8099/api/v1/register/verifyUser/");

        return "register_success";
    }

    @GetMapping("verifyUser/{code}")
    public String verifyExpert(@PathVariable Integer code) {
        if (userService.verify(code)) {
            return "verify success waiting for accept by admin";
        } else {
            return "verify fail";
        }
    }
}
