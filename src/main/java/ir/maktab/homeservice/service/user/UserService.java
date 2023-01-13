package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.base.Person;
import jakarta.mail.MessagingException;

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;

public interface UserService {

    void save(Person user);

    void register(Person user, String siteURL)
                throws MessagingException, UnsupportedEncodingException;


    boolean verify(Integer verificationCode);

    void registerUser(Person user);

    void changePassword(Person user, String password);

    Person findById(Long id);

    List<Person> findAll();

    List<Person> findByFirstname(String firstname);

    List<Person> findByLastname(String lastname);

    Person findByEmail(String email);

    List<Person> findBy(Map<String, String> find);

    boolean existsByEmail(String email);

    void delete(Person user);

    Person userDetail(Person user);

    List<Person> userFindBySpecification(Map<String, String> map);

    List<Person> userSpecification(Map<String, String> map);

    // TODO: 1/4/2023 AD
}
