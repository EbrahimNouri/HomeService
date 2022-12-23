package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.admin.AdminService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminControllerImpl implements AdminController {

    private AdminService service;
    private final BasicServicesService basicServicesService;
    private final ExpertService expertService;

    private final ExpertTypeServiceService expertTypeServiceService;
    private final TypeServiceService typeServiceService;
    private final OfferService offerService;

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
    public void descriptionChange(TypeService typeService, String description) {

    }

    @PutMapping("/acceptExpert/{expertId}")
    @Override
    public void acceptExpert(@PathVariable Long expertId) {
        Optional<Expert> acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert.orElse(null));
    }

    @Override
    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    public void removeExpertFromBasicService(@PathVariable Long expertId) {
        Expert expert = expertService.findById(expertId)
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));

        expertTypeServiceService.removeExpertFromBasicService(expert);
    }

    @PostMapping("/addExpertToTypeService")
    @Override
    public void addExpertToTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {
        expertTypeServiceService.addExpertToTypeService(findExpertTypeServiceByDto(expertTypeServiceDto));
    }

    @Override
    @DeleteMapping("/removeExpertFromTypeService/")
    public void removeExpertFromTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {

        expertTypeServiceService.removeExpertFromTypeService(findExpertTypeServiceByDto(expertTypeServiceDto));
    }


    @Override
    @GetMapping("findExpertTypeServiceById/findById/")
    public ResponseEntity<ExpertTypeService> findExpertTypeServiceById(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {

        ExpertTypeService expertTypeService = findExpertTypeServiceByDto(expertTypeServiceDto);
        return expertTypeServiceService.findById(expertTypeService.getExpert().getId()
                        , expertTypeService.getTypeService().getId()).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ExpertTypeService findExpertTypeServiceByDto(ExpertTypeServiceDto expertTypeServiceDto) {
        Expert expert = expertService.findById(expertTypeServiceDto.getExpertId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));

        TypeService typeService = typeServiceService.findById(expertTypeServiceDto.getTypeServiceId())
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found"));

        return new ExpertTypeService(expert, typeService);
    }

    @Override
    @GetMapping("findOfferById/{id}")
    public ResponseEntity<Offer> findOfferById(@PathVariable Long id) {
        return offerService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound()
                        .build());
    }

    @Override
    @GetMapping("/showAllTypeService/{id}")
    public List<TypeService> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id);
    }

    @Override
    @PostMapping("/addTypeService")
    public void addTypeService(@RequestBody TypeServiceDto typeServiceDto) {

        TypeService typeService = TypeService.builder()
                .subService(typeServiceDto.getName())
                .basicService(basicServicesService.findById(typeServiceDto.getBaseServiceId()).orElseThrow(
                        () -> new CustomExceptionNotFind("basic service not found")))
                .basePrice(typeServiceDto.getPrice())
                .build();

        typeServiceService.addSubService(typeService);
    }


    @PostMapping("/addSubService")
    @Override
    public void addSubService(@RequestBody TypeServiceDto typeServiceDto) {

        BasicService basicService = basicServicesService.findById(typeServiceDto.getBaseServiceId())
                .orElseThrow(() -> new CustomExceptionNotFind("basic service not found."));

        typeServiceService.addSubService(TypeService.builder()
                .basicService(basicService)
                .subService(typeServiceDto.getName())
                .basePrice(typeServiceDto.getPrice())
                .build());
    }

    @Override
    @PutMapping("/paymentPriceChange")
    public void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto) {
        TypeService typeService = typeServiceService.findById(paymentPriceChangeDto.getTypeServiceId())
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found."));

        typeServiceService.paymentPriceChange(typeService, paymentPriceChangeDto.getPrice());
    }

    @Override
    @GetMapping("/showTypeServices/{basicServiceId}")
    public List<TypeService> showTypeServices(@PathVariable Long basicServiceId) {
        return typeServiceService.showTypeServices(basicServiceId);
    }
}