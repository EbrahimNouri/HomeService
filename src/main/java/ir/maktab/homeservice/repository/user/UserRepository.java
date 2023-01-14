package ir.maktab.homeservice.repository.user;

import ir.maktab.homeservice.entity.base.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<Person, Long> , JpaSpecificationExecutor<Person> {

    @Query("from Person ut where ut.id =:id")
    Optional<Person> findUserByIdCustom(Long id);

    List<Person> findByFirstname(String firstname);

    List<Person> findByLastname(String lastname);

    Optional<Person> findByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);

    Optional<Person> findByUsername(String username);
    @Query("FROM Person u WHERE u.verificationCode = ?1")
    Optional<Person> findByVerificationCode(Integer code);

}
