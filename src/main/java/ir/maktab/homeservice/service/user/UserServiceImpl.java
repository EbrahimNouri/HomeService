package ir.maktab.homeservice.service.user;


import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.repository.user.UserRepository;
import jakarta.transaction.Transactional;
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
            if (!user.getPassword().equals(password)
                    && user.getPassword().equals(Objects.requireNonNull(findById(user.getId())
                    .orElse(null)).getPassword())) {
                user.setPassword(password);
                repository.save(user);

                log.debug("debug change password user {} to {} ", user, password);
            }else {
                // TODO: 12/11/2022 AD
            }
        } catch (Exception e) {

            log.error("error change password user {} to {}", user, password, e);

        }
    }

    @Override
    public Optional<User> findById(Long id){
        Optional<User> user = Optional.empty();
        try {
            user = repository.findUserById(id);
        }catch (Exception e){
            // TODO: 12/11/2022 AD
        }
        return user;
    }
}
