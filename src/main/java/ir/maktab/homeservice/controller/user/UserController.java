package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.dto.PersonChangePasswordDto;
import ir.maktab.homeservice.dto.PersonRegisterDto;
import ir.maktab.homeservice.entity.User;

public interface UserController {
    void registerUser(PersonRegisterDto personRegisterDto);

    void changePassword(PersonChangePasswordDto personChangePasswordDto);

    User findById(Long id);
}
