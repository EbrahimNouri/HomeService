package ir.maktab.homeservice.controller;

import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.service.admin.AdminService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.mail.MessagingException;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;

@RestController
@RequestMapping("/api/v1/register")
public class RegisterController {

    private final ExpertService expertService;
    private final UserService userService;
    private final AdminService adminService;

    public RegisterController(ExpertService expertService, UserService userService, AdminService adminService) {
        this.expertService = expertService;
        this.userService = userService;
        this.adminService = adminService;
    }


    @PostMapping("/expert")
    public String registerExpert(@RequestBody @Valid PersonRegisterDto personRegisterDto)
            throws MessagingException, UnsupportedEncodingException {

        Person temp = PersonRegisterDto.personDtoExpertMapping(personRegisterDto);

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

    @PostMapping("/user")
    public String registerUser(@RequestBody @Valid PersonRegisterDto personRegisterDto)
            throws MessagingException, UnsupportedEncodingException {

        Person temp = PersonRegisterDto.personDtoUserMapping(personRegisterDto);

        userService.register(temp, "http://localhost:8099/api/v1/register/verifyUser/");

        return "register_success";
    }

    @GetMapping("/verifyUser/{code}")
    public String verifyUser(@PathVariable Integer code) {
        if (userService.verify(code)) {
            return "verify_success";
        } else {
            return "verify_fail";
        }
    }

    @PostMapping("/addAdmin") //checked
    public void addAdmin(@RequestBody PersonRegisterDto admin) {
        adminService.addAdmin(PersonRegisterDto.personDtoAdminMapping(admin));
    }
}
