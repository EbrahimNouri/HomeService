package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;

import java.util.Optional;

public interface UserService {

    void mainRegisterUser(User user);

    void registerUser(User user);

    void changePassword(User user, String password);

    Optional<User> findById(Long id);
}
