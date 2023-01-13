package ir.maktab.homeservice.repository.admin;

import ir.maktab.homeservice.entity.base.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Person, Long> {

    Optional<Person> findByUsername(String username);

    boolean existsByFirstname(String name);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
