package ir.maktab.homeservice.repository.user;

import ir.maktab.homeservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> , JpaSpecificationExecutor<User> {

    @Query("from user_table ut where ut.id =:id")
    Optional<User> findUserByIdCustom(Long id);

    List<User> findByFirstname(String firstname);

    List<User> findByLastname(String lastname);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<User> findByUsername(String username);
    @Query("FROM user_table u WHERE u.verificationCode = ?1")
    Optional<User> findByVerificationCode(Integer code);

}
