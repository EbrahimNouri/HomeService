package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;

public interface UserService {

    void save(User user);

    void mainRegisterUser(User user);

    void registerUser(User user);

    void changePassword(User user, String password);

    User findById(Long id);
}
