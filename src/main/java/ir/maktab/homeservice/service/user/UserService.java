package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;

public interface UserService {

    void registerUser(User user);

    void changePassword(User user, String password);

}
