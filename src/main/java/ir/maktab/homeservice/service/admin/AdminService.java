package ir.maktab.homeservice.service.admin;

import ir.maktab.homeservice.entity.Admin;

public interface AdminService {
    void addAdmin(Admin admin);
    void changePassword(Admin admin, String password);

}
