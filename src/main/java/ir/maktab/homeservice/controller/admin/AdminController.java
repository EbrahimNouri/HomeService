package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.BasicServiceDto;
import ir.maktab.homeservice.dto.adminPassDto;
import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.entity.BasicService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface AdminController {

    void addAdmin(Admin admin);
    void changePassword(@RequestBody @Valid adminPassDto admin);

    Admin findById(Long adminId);

    @PostMapping("/add")
    void addBasicService(@RequestBody @Valid BasicService basicService);

    @GetMapping("/all")
    List<BasicServiceDto> showAllBasicServices();
}