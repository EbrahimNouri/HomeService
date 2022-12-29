package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;

import java.util.List;
import java.util.Map;

public interface UserService {

    void save(User user);

    void mainRegisterUser(User user);

    void registerUser(User user);

    void changePassword(User user, String password);

    User findById(Long id);

    List<User> findAll();

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    User findByEmail(String email);

    List<User> findBy(Map<String, String> find);

    boolean existsByEmail(String email);
}
