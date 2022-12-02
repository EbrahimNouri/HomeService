package ir.maktab.homeservice.Service;

import ir.maktab.homeservice.entity.User;
import ir.maktab.homeservice.repository.UserRepository;
import ir.maktab.homeservice.repository.UserRepositoryImpl;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {
    UserRepository userRepository;
    UserRepositoryImpl userRepositoryImpl;

    @Transactional
    public void save(User user){
        userRepositoryImpl.saveeed(user);
    }
}
