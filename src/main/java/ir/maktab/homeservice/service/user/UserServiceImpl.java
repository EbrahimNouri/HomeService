package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.exception.CustomExceptionSave;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

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
        try {
            if (user.getEmail() != null && user.getPassword() != null) {

                repository.save(user);

                log.debug("debug user service iml {} ", user);
            } else {
                log.warn("warn register user  username or password is is null {} ", user);

                throw new CustomExceptionSave("user not saved");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void changePassword(@Valid User user, String password) {
        try {
            if (!user.getPassword().equals(password)
                    && user.getPassword().equals(Objects.requireNonNull(findById(user.getId())
                    .orElse(null)).getPassword())) {
                user.setPassword(password);
                repository.save(user);

                log.debug("debug change password user {} to {} ", user, password);
            } else {
                throw new CustomExceptionSave("password not changed");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<User> findById(Long id) {
        try {
            return repository.findUserById(id);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Optional.empty();
    }

}
