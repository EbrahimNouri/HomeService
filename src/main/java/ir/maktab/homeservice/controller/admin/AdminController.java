package ir.maktab.homeservice.controller.admin;



import ir.maktab.homeservice.dto.adminPassDto;
import ir.maktab.homeservice.entity.Admin;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Optional;

public interface AdminController {

    void addAdmin(Admin admin);
    void changePassword(@RequestBody @Valid adminPassDto admin);

    Admin findById(Long adminId);
}