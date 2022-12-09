package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.repository.user.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.stereotype.Service;

@Service
@Log4j2
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository repository;

    @Override
    public void registerUser(User user) {
        try {
            if (user.getEmail() != null && user.getPassword() != null) {

                repository.save(user);

                log.debug("debug user service iml {} ", user);
            } else
                log.warn("warn register user  username or password is is null {} ", user);

        } catch (Exception e) {

            log.error("error register user {} ", user, e);
        }

    }

    @Override
    public void changePassword(User user, String password) {
        try {

            user.setPassword(password);
            repository.save(user);

            log.debug("debug change password user {} to {} ", user, password);
        } catch (Exception e) {

            log.error("error change password user {} to {}", user, password, e);

        }
    }
}
