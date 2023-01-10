package ir.maktab.homeservice.repository.admin;

import ir.maktab.homeservice.entity.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin, Long> {

    Optional<Admin> findByUsername(String username);

    boolean existsByName(String name);
    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
