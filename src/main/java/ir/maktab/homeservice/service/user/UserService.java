package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface UserService {

    void save(User user);

    void register(User user, String siteURL)
                throws MessagingException, UnsupportedEncodingException;


    boolean verify(Integer verificationCode);

    void registerUser(User user);

    void changePassword(User user, String password);

    User findById(Long id);

    List<User> findAll();

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    User findByEmail(String email);

    List<User> findBy(Map<String, String> find);

    boolean existsByEmail(String email);

    void delete(User user);

    User userDetail(User user);

    // TODO: 1/4/2023 AD
}
