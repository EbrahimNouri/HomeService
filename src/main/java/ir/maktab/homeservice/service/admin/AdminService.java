package ir.maktab.homeservice.service.admin;

import ir.maktab.homeservice.entity.base.Person;

public interface AdminService{
    void addAdmin(Person admin);
    void changePassword(Person admin, String password);

    Person findById(Long adminId);

}
