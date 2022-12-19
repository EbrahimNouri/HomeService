package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.user.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/user")
public class UserControllerImpl implements UserController {

    private UserService userService;

    @Override
    @PostMapping("/add")
    public void registerUser(@RequestBody PersonRegisterDto personRegisterDto) {
        User temp = User.builder()
                .firstname(personRegisterDto.getFirstname())
                .lastname(personRegisterDto.getLastname())
                .email(personRegisterDto.getEmail())
                .password(personRegisterDto.getPassword())
                .credit(0)
                .build();

        userService.mainRegisterUser(temp);
    }

    @Override
    @PutMapping("/chPass")
    public void changePassword(@RequestBody PersonChangePasswordDto personChangePasswordDto) {
        User temp = userService.findById(personChangePasswordDto.getId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));
        userService.changePassword(temp, personChangePasswordDto.getPassword());
    }

    @Override
    @GetMapping("{id}")
    public User findById(@PathVariable Long id) {
        return userService.findById(id).orElseThrow(() -> new CustomExceptionNotFind("user not found"));
    }
}
