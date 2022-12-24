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
import ir.maktab.homeservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminControllerImpl implements AdminController {

    private final AdminService service;
    private final BasicServicesService basicServicesService;
    private final ExpertService expertService;

    private final UserService userService;
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
        Admin id_not_found = service.findById(admin.getId());
        service.changePassword(id_not_found, admin.getPassword());
    }

    @GetMapping("/{id}")
    @Override
    public Admin findById(@PathVariable Long id) {
//        return new ResponseEntity.ok(service.findById(adminId)
        return service.findById(id);
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
    @PutMapping("/descriptionChange/{typeServiceId}/{description}")
    public void descriptionChange(@PathVariable Long typeServiceId,@PathVariable String description) {
        typeServiceService.descriptionChange(typeServiceId ,description);
    }

    @PutMapping("/acceptExpert/{expertId}")
    @Override
    public void acceptExpert(@PathVariable Long expertId) {
        Expert acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert);
    }

    @Override
    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    public HttpStatus removeExpertFromBasicService(@PathVariable Long expertId) {
        Expert expert = expertService.findById(expertId);

        return expertTypeServiceService.removeExpertFromBasicService(expert) == 1
                ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
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
    @GetMapping("findExpertTypeServiceById/{expertId}/{typeServiceId}")
    public ExpertTypeServiceMapped findExpertTypeServiceById(@PathVariable Long expertId
            , @PathVariable Long typeServiceId) {


        ExpertTypeService expertTypeService = expertTypeServiceService.findById
                (expertId, typeServiceId);
        Expert expert = expertTypeService.getExpert();
        TypeService typeService = expertTypeService.getTypeService();
        ExpertTypeServiceMapped expertTypeServiceMapped = new ExpertTypeServiceMapped
                (expert.getFirstname()
                        , expert.getLastname()
                        , expert.getEmail()
                        ,expert.getAverageScore()
/*
                        , FileUtil.fileWriter(Path.of("/Users/ebrahimnouri/Desktop/text.jpg"), expert.getAvatar())
*/
                        , expert.getCredit()
                        , expert.getAverageScore()
                        , typeService.getSubService()
                        , typeService.getBasePrice()
                        ,typeService.getBasicService().getName()
                        ,typeService.getDescription()
                        );

        return expertTypeServiceMapped;
    }

    private ExpertTypeService findExpertTypeServiceByDto(ExpertTypeServiceDto expertTypeServiceDto) {
        Expert expert = expertService.findById(expertTypeServiceDto.getExpertId());

        TypeService typeService = typeServiceService.findById(expertTypeServiceDto.getTypeServiceId());

        return new ExpertTypeService(expert, typeService);
    }

    @Override
    @GetMapping("findOfferById/{id}")
    public Offer findOfferById(@PathVariable Long id) {
        return offerService.findById(id);

    }

    @Override
    // TODO: 12/24/2022 AD  
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
        TypeService typeService = typeServiceService.findById(paymentPriceChangeDto.getTypeServiceId());

        typeServiceService.paymentPriceChange(typeService, paymentPriceChangeDto.getPrice());
    }

    @Override
    @GetMapping("/showTypeServices/{basicServiceId}")
    public List<TypeService> showTypeServices(@PathVariable Long basicServiceId) {
        return typeServiceService.showTypeServices(basicServiceId);
    }

    // TODO: 12/24/2022 AD postman
    @Override
    @GetMapping("/findByFirstName/allExperts")
    public List<PersonFindDto> allExperts(){
        PersonFindDto personFindDto = new PersonFindDto();
        List<Expert> experts = expertService.findAll();
        List<User> users =  userService.findAll();
        return null;
    }
    @Override
    @GetMapping("/findByFirstName/allUsers")
    public List<PersonFindDto> allUsers(){
        PersonFindDto personFindDto = new PersonFindDto();
        List<Expert> experts = expertService.findAll();
        List<User> users =  userService.findAll();
        return null;
    }

    @Override
    @GetMapping("/findByFirstName/{firstname}")
    public List<PersonFindDto> findByFirstName(@PathVariable String firstname){
        PersonFindDto personFindDto = new PersonFindDto();
        List<Expert> experts = expertService.findByFirstName(firstname);
        List<User> users =  userService.findByFirstname(firstname);
        return null;
    }

    @Override
    @GetMapping("/findByLastname/{lastname}")
    public List<PersonFindDto> findByLastName(@PathVariable String lastname){
        PersonFindDto personFindDto = new PersonFindDto();
        List<Expert> experts = expertService.findByLastName(lastname);
        List<User> users =  userService.findByLastname(lastname);
        return null;
    }

    @Override
    @GetMapping("/findByEmail/{email}")
    public List<PersonFindDto> findByEmail(@PathVariable String email){
        PersonFindDto personFindDto = new PersonFindDto();
        Expert expert = expertService.findByEmail(email);
        User user =  userService.findByEmail(email);
        return null;
    }
}