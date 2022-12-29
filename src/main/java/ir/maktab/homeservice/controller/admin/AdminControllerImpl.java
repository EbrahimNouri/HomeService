package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
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
public class AdminControllerImpl {

    private final AdminService service;
    private final BasicServicesService basicServicesService;
    private final ExpertService expertService;

    private final UserService userService;
    private final ExpertTypeServiceService expertTypeServiceService;
    private final TypeServiceService typeServiceService;
    private final OfferService offerService;

    @PostMapping("/add")
    public void addAdmin(@RequestBody @Valid Admin admin) {
        service.addAdmin(admin);
    }

    @PutMapping("/chPass")
    public void changePassword(@RequestBody @Valid adminPassDto admin) {
        Admin id_not_found = service.findById(admin.getId());
        service.changePassword(id_not_found, admin.getPassword());
    }

    @GetMapping("/{id}")
    public Admin findById(@PathVariable Long id) {
//        return new ResponseEntity.ok(service.findById(adminId)
        return service.findById(id);
    }

    @PostMapping("/addBasicService")
    public void addBasicService(@RequestBody @Valid BasicService basicService) {
        basicServicesService.addBasicService(basicService);
    }

    @GetMapping("/showAllBasicServices")
    public List<BasicServiceDto> showAllBasicServices() {
        return basicServicesService.findAll().stream()
                .map(bs -> new BasicServiceDto(bs.getId(), bs.getName())).toList();
    }

    @PutMapping("/descriptionChange/{typeServiceId}/{description}")
    public void descriptionChange(@PathVariable Long typeServiceId, @PathVariable String description) {
        typeServiceService.descriptionChange(typeServiceId, description);
    }

    @PutMapping("/acceptExpert/{expertId}")
    public void acceptExpert(@PathVariable Long expertId) {
        Expert acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert);
    }

    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    public HttpStatus removeExpertFromBasicService(@PathVariable Long expertId) {
        Expert expert = expertService.findById(expertId);

        return expertTypeServiceService.removeExpertFromBasicService(expert) == 1
                ? HttpStatus.OK : HttpStatus.BAD_REQUEST;
    }

    @PostMapping("/addExpertToTypeService")
    public void addExpertToTypeService(@RequestParam Long ex, @RequestParam Long ty) {
        expertTypeServiceService.addExpertToTypeService
                (findExpertTypeServiceByDto(new ExpertTypeServiceDto(ex, ty)));
    }

    @DeleteMapping("/removeExpertFromTypeService/")
    public void removeExpertFromTypeService(@RequestParam Long ex, @RequestParam Long ty) {

        expertTypeServiceService.removeExpertFromTypeService
                (findExpertTypeServiceByDto(new ExpertTypeServiceDto(ex, ty)));
    }


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
                        // TODO: 12/28/2022 AD i need that for test avatar
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

    @GetMapping("findOfferById/{id}")
    public OfferDto findOfferById(@PathVariable Long id) {

        Offer offer = offerService.findById(id);
         return OfferDto.builder()
                .description(offer.getDescription())
                .endDate(offer.getEndDate())
                .startDate(offer.getStartDate())
                .suggestedPrice(offer.getSuggestedPrice())
                .expertId(offer.getExpert().getId())
                .orderId(offer.getOrder().getId())
                .build();

    }

    // TODO: 12/24/2022 AD

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream().map(this::typeServiceMapped).toList();
    }

    @PostMapping("/addTypeService")
    public void addTypeService(@RequestParam String name, Long baseId, Double price) {

        TypeService typeService = TypeService.builder()
                .subService(name)
                .basicService(basicServicesService.findById(baseId))
                .basePrice(price)
                .build();

        typeServiceService.addSubService(typeService);
    }


    @PostMapping("/addSubService")
    public void addSubService(@RequestBody TypeServiceDto typeServiceDto) {

        BasicService basicService = basicServicesService.findById(typeServiceDto.getBaseServiceId());

        typeServiceService.addSubService(TypeService.builder()
                .basicService(basicService)
                .subService(typeServiceDto.getName())
                .basePrice(typeServiceDto.getPrice())
                .build());
    }

    @PutMapping("/paymentPriceChange")
    public void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto) {
        TypeService typeService = typeServiceService.findById(paymentPriceChangeDto.getTypeServiceId());

        typeServiceService.paymentPriceChange(typeService, paymentPriceChangeDto.getPrice());
    }

    @GetMapping("/showTypeServices/{basicServiceId}")
    public List<TypeService> showTypeServices(@PathVariable Long basicServiceId) {
        return typeServiceService.showTypeServices(basicServiceId);
    }

    // TODO: 12/24/2022 AD postman
    @GetMapping("/all")
    public List<personFindDto> showAll() {
        List<personFindDto> all = new ArrayList<>();
        all.addAll(expertService.findAll().stream().map(this::expertMapping).toList());
        all.addAll(userService.findAll().stream().map(this::userMapping).toList());
        return all;
    }

    @GetMapping("/findByFirstName/")
    public List<personFindDto> findByFirstNameAll(@RequestParam String firstname) {
        List<personFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByFirstName(firstname).stream().map(this::expertMapping).toList());
        all.addAll(userService.findByFirstname(firstname).stream().map(this::userMapping).toList());
        return all;
    }


    @GetMapping("/findByFirstName/allExperts/{firstname}")
    public List<personFindDto> findByFirstnameExpert(@PathVariable String firstname) {

        return expertService.findByFirstName(firstname).stream().map(this::expertMapping).toList();

    }

    @GetMapping("/findByFirstName/allUsers/{firstname}")
    public List<personFindDto> findByFirstnameUser(@PathVariable String firstname) {
        return userService.findByFirstname(firstname).stream().map(this::userMapping).toList();

    }

    @GetMapping("/findByLastnameAll/{lastname}")
    public List<personFindDto> findByLastName(@PathVariable String lastname) {
        List<personFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByLastName(lastname).stream().map(this::expertMapping).toList());
        all.addAll(userService.findByLastname(lastname).stream().map(this::userMapping).toList());
        return all;
    }

    @GetMapping("/findByLastname/allExperts/{lastname}")
    public List<personFindDto> findByLastnameExpert(@PathVariable String lastname) {

        return expertService.findByLastName(lastname).stream().map(this::expertMapping).toList();

    }

    @GetMapping("/findByLastname/allUsers/{lastname}")
    public List<personFindDto> findByLastnameUser(@PathVariable String lastname) {
        return userService.findByLastname(lastname).stream().map(this::userMapping).toList();

    }

    @GetMapping("/findByEmailAll/{email}")
    public personFindDto[] findByEmailExpert(@PathVariable String email) {
        personFindDto[] persons = new personFindDto[2];
        persons[0] = Optional.of(expertService.findByEmail(email)).map(this::expertMapping).orElse(null);
        persons[1] = Optional.of(userService.findByEmail(email)).map(this::userMapping).orElse(null);
        return persons;
    }

    @GetMapping("/findByEmailExpert/{email}")
    public personFindDto findByEmailAll(@PathVariable String email) {
        return Optional.of(expertService.findByEmail(email)).map(this::expertMapping).orElse(null);
    }

    @GetMapping("/findByEmailUser/{email}")
    public personFindDto findByEmailUser(@PathVariable String email) {
        return Optional.of(userService.findByEmail(email)).map(this::userMapping).orElse(null);
    }

    @GetMapping("/finAllReqParam")
    public List<personFindDto> findAllReq(@RequestParam Map<String, String> stringMap) {
        List<personFindDto> findDtos = new ArrayList<>();
        findDtos.addAll(expertService.findBy(stringMap).stream().map(this::expertMapping).toList());
        findDtos.addAll(userService.findBy(stringMap).stream().map(this::userMapping).toList());
        return findDtos;
    }
    @GetMapping("/findAllExpertReq")
    public List<personFindDto> findAllExpertReq(@RequestParam Map<String, String> stringMap) {
        return expertService.findBy(stringMap).stream().map(this::expertMapping).toList();
    }
    @GetMapping("/findAllUserReq")
    public List<personFindDto> findAllUserReq(@RequestParam Map<String, String> stringMap) {
        return userService.findBy(stringMap).stream().map(this::userMapping).toList();
    }

    private personFindDto expertMapping(Expert expert) {
        return personFindDto.builder()
                .id(expert.getId())
                .firstname(expert.getFirstname())
                .lastname(expert.getLastname())
                .personType(PersonType.EXPERT)
                .email(expert.getEmail())
                .credit(expert.getCredit())
                .signupDate(expert.getSignupDateTime())
                .build();
    }

    private personFindDto userMapping(User user) {
        return personFindDto.builder()
                .id(user.getId())
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .personType(PersonType.USER)
                .email(user.getEmail())
                .credit(user.getCredit())
                .signupDate(user.getSignupDateTime())
                .build();
    }

    public TypeServiceDto typeServiceMapped(TypeService typeService) {
        return TypeServiceDto.builder()
                .typeServiceId(typeService.getId())
                .baseServiceId(typeService.getBasicService().getId())
                .name(typeService.getSubService())
                .price(typeService.getBasePrice())
                .build();
    }

    @GetMapping("/findByBasicService/{basicServiceId}")
    public List<personFindDto> findByBasicService(@PathVariable Long basicServiceId){
       return expertService.findByBasicService(basicServiceId).stream().map(this::expertMapping).toList();
    }
}