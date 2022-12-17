package ir.maktab.homeservice.controller.basicServices;

import ir.maktab.homeservice.dto.BasicServiceDto;
import ir.maktab.homeservice.entity.BasicService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface BasicServicesController {


    @PostMapping("/add")
    void addBasicService(@RequestBody BasicService basicService);

    @GetMapping("/all")
    List<BasicServiceDto> showAllBasicServices();
}
