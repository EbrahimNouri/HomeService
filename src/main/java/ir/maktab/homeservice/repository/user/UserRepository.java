package ir.maktab.homeservice.repository.user;

import ir.maktab.homeservice.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}
