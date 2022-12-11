package ir.maktab.homeservice.service.admin;

import ir.maktab.homeservice.entity.Admin;

import java.util.Optional;

public interface AdminService {
    void addAdmin(Admin admin);
    void changePassword(Admin admin, String password);

    Optional<Admin> findById(Long adminId);
}
