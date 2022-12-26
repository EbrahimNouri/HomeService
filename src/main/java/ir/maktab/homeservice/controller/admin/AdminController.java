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
import java.util.Map;

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

    void descriptionChange(Long typeServiceId, String description);

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
    List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id);

    @PostMapping("/addTypeService")
    void addTypeService(@RequestBody TypeServiceDto typeServiceDto);

    @PostMapping("/addSubService")
    void addSubService(@RequestBody TypeServiceDto typeServiceDto);

    @PutMapping("/paymentPriceChange")
    void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto);

    @GetMapping("/showTypeServices/{basicServiceId}")
    List<TypeService> showTypeServices(@PathVariable Long basicServiceId);

    @GetMapping("/findByFirstName/allExperts/{firstname}")
    List<PersonFindDto> findByFirstnameExpert(@PathVariable String firstname);

    @GetMapping("/findByFirstName/allExperts/{lastname}")
    List<PersonFindDto> findByLastnameExpert(@PathVariable String lastname);

    @GetMapping("/findByFirstName/allUsers/{firstname}")
    List<PersonFindDto> findByFirstnameUser(@PathVariable String firstname);

    // TODO: 12/24/2022 AD postman
    @GetMapping("/all")
    List<PersonFindDto> showAll();

    @GetMapping("/findByFirstName/{firstname}")
    List<PersonFindDto> findByFirstNameAll(@PathVariable String firstname);

    @GetMapping("/findByLastname/{lastname}")
    List<PersonFindDto> findByLastName(@PathVariable String lastname);

    @GetMapping("/findByFirstName/allUsers/{lastname}")
    List<PersonFindDto> findByLastnameUser(@PathVariable String lastname);

    @GetMapping("/findByEmailAll/{email}")
    PersonFindDto[] findByEmailExpert(@PathVariable String email);

    @GetMapping("/findByEmailExpert/{email}")
    abstract PersonFindDto findByEmailAll(@PathVariable String email);

    @GetMapping("/findByEmailUser/{email}")
    PersonFindDto findByEmailUser(@PathVariable String email);

    @GetMapping("/finAllNew")
    List<PersonFindDto> findAllNew(@RequestParam Map<String, String> dsad);
}