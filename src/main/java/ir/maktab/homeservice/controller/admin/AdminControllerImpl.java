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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    public void descriptionChange(@PathVariable Long typeServiceId, @PathVariable String description) {
        typeServiceService.descriptionChange(typeServiceId, description);
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

        return new ExpertTypeServiceMapped
                (expert.getFirstname()
                        , expert.getLastname()
                        , expert.getEmail()
                        , expert.getAverageScore()
/*
                        , FileUtil.fileWriter(Path.of("/Users/ebrahimnouri/Desktop/text.jpg"), expert.getAvatar())
*/
                        , expert.getCredit()
                        , expert.getAverageScore()
                        , typeService.getSubService()
                        , typeService.getBasePrice()
                        , typeService.getBasicService().getName()
                        , typeService.getDescription()
                );
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

    // TODO: 12/24/2022 AD

    @Override
    @GetMapping("/showAllTypeService/{id}")
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream().map(this::typeServiceMapped).toList();
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
    @GetMapping("/all")
    public List<PersonFindDto> showAll() {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findAll().stream().map(this::expertMapping).toList());
        all.addAll(userService.findAll().stream().map(this::userMapping).toList());
        return all;
    }

    @Override
    @GetMapping("/findByFirstName/")
    public List<PersonFindDto> findByFirstNameAll(@RequestParam String firstname) {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByFirstName(firstname).stream().map(this::expertMapping).toList());
        all.addAll(userService.findByFirstname(firstname).stream().map(this::userMapping).toList());
        return all;
    }


    @Override
    @GetMapping("/findByFirstName/allExperts/{firstname}")
    public List<PersonFindDto> findByFirstnameExpert(@PathVariable String firstname) {

        return expertService.findByFirstName(firstname).stream().map(this::expertMapping).toList();

    }

    @Override
    @GetMapping("/findByFirstName/allUsers/{firstname}")
    public List<PersonFindDto> findByFirstnameUser(@PathVariable String firstname) {
        return userService.findByFirstname(firstname).stream().map(this::userMapping).toList();

    }

    @Override
    @GetMapping("/findByLastnameAll/{lastname}")
    public List<PersonFindDto> findByLastName(@PathVariable String lastname) {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByLastName(lastname).stream().map(this::expertMapping).toList());
        all.addAll(userService.findByLastname(lastname).stream().map(this::userMapping).toList());
        return all;
    }

    @Override
    @GetMapping("/findByLastname/allExperts/{lastname}")
    public List<PersonFindDto> findByLastnameExpert(@PathVariable String lastname) {

        return expertService.findByLastName(lastname).stream().map(this::expertMapping).toList();

    }

    @Override
    @GetMapping("/findByLastname/allUsers/{lastname}")
    public List<PersonFindDto> findByLastnameUser(@PathVariable String lastname) {
        return userService.findByLastname(lastname).stream().map(this::userMapping).toList();

    }

    @Override
    @GetMapping("/findByEmailAll/{email}")
    public PersonFindDto[] findByEmailExpert(@PathVariable String email) {
        PersonFindDto[] persons = new PersonFindDto[2];
        persons[0] = Optional.of(expertService.findByEmail(email)).map(this::expertMapping).orElse(null);
        persons[1] = Optional.of(userService.findByEmail(email)).map(this::userMapping).orElse(null);
        return persons;
    }

    @Override
    @GetMapping("/findByEmailExpert/{email}")
    public PersonFindDto findByEmailAll(@PathVariable String email) {
        return Optional.of(expertService.findByEmail(email)).map(this::expertMapping).orElse(null);
    }

    @Override
    @GetMapping("/findByEmailUser/{email}")
    public PersonFindDto findByEmailUser(@PathVariable String email) {
        return Optional.of(userService.findByEmail(email)).map(this::userMapping).orElse(null);
    }

    @Override
    @GetMapping("/finAllNew")
    public List<PersonFindDto> findAllNew(@RequestParam Map<String, String> dsad){
        return expertService.findBy(dsad).stream().map(this::expertMapping).toList();
    }

    private PersonFindDto expertMapping(Expert expert) {
        return PersonFindDto.builder()
                .id(expert.getId())
                .firstname(expert.getFirstname())
                .lastname(expert.getLastname())
                .personType(PersonType.EXPERT)
                .email(expert.getEmail())
                .credit(expert.getCredit())
                .signupDate(expert.getSignupDateTime())
                .build();
    }

    private PersonFindDto userMapping(User user) {
        return PersonFindDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .personType(PersonType.USER)
                .email(user.getEmail())
                .credit(user.getCredit())
                .signupDate(user.getSignupDateTime())
                .build();
    }

    private TypeServiceDto typeServiceMapped(TypeService typeService){
        return TypeServiceDto.builder()
                .typeServiceId(typeService.getId())
                .baseServiceId(typeService.getBasicService().getId())
                .name(typeService.getSubService())
                .price(typeService.getBasePrice())
                .build();
    }
}