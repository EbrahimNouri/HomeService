package ir.maktab.homeservice.repository.user;

import ir.maktab.homeservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findUserById(Long id);

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    Optional<User> findByEmail(String email);


}
