package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.Admin;
import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.Offer;
import ir.maktab.homeservice.entity.TypeService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

public interface AdminController {

    void addAdmin(Admin admin);
    void changePassword(@RequestBody @Valid adminPassDto admin);

    @GetMapping("findOfferById/{id}")
    Offer findOfferById(@PathVariable Long id);

    @GetMapping("/{id}")
    Admin findById(@PathVariable Long id);

    @PostMapping("addBasicService/add")
    void addBasicService(@RequestBody @Valid BasicService basicService);

    @GetMapping("/showAllBasicServices")
    List<BasicServiceDto> showAllBasicServices();

    void descriptionChange(TypeService typeService, String description);

    @PutMapping("/acceptExpert/{expertId}")
    void acceptExpert(@PathVariable Long expertId);

    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    HttpStatus removeExpertFromBasicService(@PathVariable Long expertId);

    @PostMapping("/addExpertToTypeService")
    void addExpertToTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto);

    @DeleteMapping("/removeExpertFromTypeService/")
    void removeExpertFromTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto);

    @GetMapping("findExpertTypeServiceById/{expertId}/{typeServiceId}")
    ExpertTypeServiceMapped findExpertTypeServiceById(@PathVariable Long expertId, @PathVariable Long typeServiceId);

    @GetMapping("/showAllTypeService/{id}")
    List<TypeService> findByBasicServiceId(@PathVariable Long id);

    @PostMapping("/addTypeService")
    void addTypeService(@RequestBody TypeServiceDto typeServiceDto);

    @PostMapping("/addSubService")
    void addSubService(@RequestBody TypeServiceDto typeServiceDto);

    @PutMapping("/paymentPriceChange")
    void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto);

    @GetMapping("/showTypeServices/{basicServiceId}")
    List<TypeService> showTypeServices(@PathVariable Long basicServiceId);
}