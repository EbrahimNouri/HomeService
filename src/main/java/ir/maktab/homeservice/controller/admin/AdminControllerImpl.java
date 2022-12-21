package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.BasicServiceDto;
import ir.maktab.homeservice.dto.adminPassDto;
import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.admin.AdminService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminControllerImpl implements AdminController {

    private AdminService service;
    private final BasicServicesService basicServicesService;


    @PostMapping("/add")
    @Override
    public void addAdmin(@RequestBody @Valid Admin admin) {
        service.addAdmin(admin);
    }

    @PutMapping("/chPass")
    @Override
    public void changePassword(@RequestBody @Valid adminPassDto admin) {
        Admin id_not_found = service.findById(admin.getId())
                .orElseThrow(() -> new CustomExceptionNotFind("id not found"));

        service.changePassword(id_not_found, admin.getPassword());
    }

    @GetMapping("/{id}")
    @Override
    public Admin findById(@PathVariable Long id) {
//        return new ResponseEntity.ok(service.findById(adminId)
        return service.findById(id).orElseThrow(() -> new CustomExceptionNotFind("id not found"));
    }

    @Override
    @PostMapping("/addBasicService")
    public void addBasicService(@RequestBody @Valid BasicService basicService) {
        basicServicesService.addBasicService(basicService);
    }

    @Override
    @GetMapping("/showAllBasicServices")
    public List<BasicServiceDto> showAllBasicServices() {
        return basicServicesService.findAll().stream()
                .map(bs -> new BasicServiceDto(bs.getId(), bs.getName())).toList();
    }

    @Override
    public void descriptionChange(TypeService typeService, String description){

    }
}