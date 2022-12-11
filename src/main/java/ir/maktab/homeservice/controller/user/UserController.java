package ir.maktab.homeservice.controller.user;


import ir.maktab.homeservice.entity.User;

public interface UserController {

    void registerUser(User user);

    void changePassword(User user, String password);

}
