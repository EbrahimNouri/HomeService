package ir.maktab.homeservice.repository;

import ir.maktab.homeservice.entity.base.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserDetailRepository extends JpaRepository<Person, Long> {
    Optional<Person> findByUsername(String username);
}
