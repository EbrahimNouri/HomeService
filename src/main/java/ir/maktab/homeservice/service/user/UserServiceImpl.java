package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@Log4j2
@AllArgsConstructor
@Transactional
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public void save(User user) {
        repository.save(user);
    }

    @Override
    public void mainRegisterUser(@Valid User user) {
        repository.save(user);
    }

    @Override
    public void registerUser(@Valid User user) {

        if (user.getEmail() == null && user.getPassword() == null)
            throw new CustomExceptionSave("user not saved");

        repository.save(user);

        log.debug("debug user service iml {} ", user);

    }

    @Override
    public void changePassword(@Valid User user, String password) {

        if (!user.getPassword().equals(password)
                && user.getPassword().equals(Objects.requireNonNull(findById(user.getId())
                .getPassword()))) {

            user.setPassword(password);
            repository.save(user);

            log.debug("debug change password user {} to {} ", user, password);
        } else {
            throw new CustomExceptionSave("password not changed");
        }
    }

    @Override
    public User findById(Long id) {

        return repository.findUserById(id).orElseThrow
                (() -> new RuntimeException("user not found"));

    }

    @Override
    public List<User> findAll() {
        return repository.findAll();
    }

    @Override
    public List<User> findByFirstname(String firstname) {
        return repository.findByFirstname(firstname);
    }

    @Override
    public List<User> findByLastname(String lastname) {
        return repository.findByLastname(lastname);
    }

    @Override
    public User findByEmail(String email) {
        return repository.findByEmail(email).orElseThrow(() ->
                new CustomExceptionNotFind("user not found"));
    }

}
