package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
import ir.maktab.homeservice.entity.base.Person;
import ir.maktab.homeservice.service.admin.AdminService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.offer.OfferService;
import ir.maktab.homeservice.service.order.OrderService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import ir.maktab.homeservice.service.user.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
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
    private final OrderService orderService;



    @PutMapping("/chPass") //checked
    public void changePassword(@RequestParam @Valid String password, Authentication authentication) {
        Person admin = (Person) authentication.getPrincipal();
        service.changePassword(admin, password);
    }

    @GetMapping() //checked
    public AdminDto admin(Authentication authentication) {
        Person admin = (Person) authentication.getPrincipal();
        return adminDtoMapper(admin);
    }

    @PostMapping("/addBasicService") //checked
    public void addBasicService(@RequestBody @Valid BasicService basicService) {
        basicServicesService.addBasicService(basicService);
    }

    @GetMapping("/showAllBasicServices") //checked
    public List<BasicServiceDto> showAllBasicServices() {
        return basicServicesService.findAll().stream()
                .map(bs -> new BasicServiceDto(bs.getId(), bs.getName())).toList();
    }

    @PutMapping("/descriptionChange/{typeServiceId}/{description}") //checked
    public void descriptionChange(@PathVariable Long typeServiceId, @PathVariable String description) {
        typeServiceService.descriptionChange(typeServiceId, description);
    }

    @PutMapping("/acceptExpert/{expertId}") //checked
    public void acceptExpert(@PathVariable Long expertId) {
        Person acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert);
    }

    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    public HttpStatus removeExpertFromBasicService(@PathVariable Long expertId) {
        Person expert = expertService.findById(expertId);

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
        Person expert = expertTypeService.getExpert();
        TypeService typeService = expertTypeService.getTypeService();

        return new ExpertTypeServiceMapped
                (expert.getFirstname()
                        , expert.getLastname()
                        , expert.getEmail()
                        , expert.getAverageScore()
                        , expert.getCredit()
                        , expert.getAverageScore()
                        , typeService.getSubService()
                        , typeService.getBasePrice()
                        , typeService.getBasicService().getName()
                        , typeService.getDescription()
                );
    }

    private ExpertTypeService findExpertTypeServiceByDto(ExpertTypeServiceDto expertTypeServiceDto) {
        Person expert = expertService.findById(expertTypeServiceDto.getExpertId());

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

    @GetMapping("/all")
    public List<PersonFindDto> showAll() {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findAll().stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        all.addAll(userService.findAll().stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return all;
    }

    @GetMapping("/findByFirstName/")
    public List<PersonFindDto> findByFirstNameAll(@RequestParam String firstname) {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByFirstName(firstname).stream()
                .map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        all.addAll(userService.findByFirstname(firstname).stream()
                .map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return all;
    }


    @GetMapping("/findByFirstName/allExperts/{firstname}")
    public List<PersonFindDto> findByFirstnameExpert(@PathVariable String firstname) {

        return expertService.findByFirstName(firstname).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByFirstName/allUsers/{firstname}")
    public List<PersonFindDto> findByFirstnameUser(@PathVariable String firstname) {
        return userService.findByFirstname(firstname).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByLastnameAll/{lastname}")
    public List<PersonFindDto> findByLastName(@PathVariable String lastname) {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByLastName(lastname).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        all.addAll(userService.findByLastname(lastname).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return all;
    }

    @GetMapping("/findByLastname/allExperts/{lastname}")
    public List<PersonFindDto> findByLastnameExpert(@PathVariable String lastname) {

        return expertService.findByLastName(lastname).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByLastname/allUsers/{lastname}")
    public List<PersonFindDto> findByLastnameUser(@PathVariable String lastname) {
        return userService.findByLastname(lastname).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByEmailAll/{email}")
    public PersonFindDto[] findByEmailExpert(@PathVariable String email) {
        PersonFindDto[] persons = new PersonFindDto[2];
        persons[0] = Optional.of(expertService.findByEmail(email)).map(PersonFindDto::expertTopPersonFindDtoMapper)
                .orElse(null);
        persons[1] = Optional.of(userService.findByEmail(email)).map(PersonFindDto::userTopPersonFindDtoMapper)
                .orElse(null);
        return persons;
    }

    @GetMapping("/findByEmailExpert/{email}")
    public PersonFindDto findByEmailAll(@PathVariable String email) {
        return Optional.of(expertService.findByEmail(email)).map(PersonFindDto::expertTopPersonFindDtoMapper).orElse(null);
    }

    @GetMapping("/findByEmailUser/{email}")
    public PersonFindDto findByEmailUser(@PathVariable String email) {
        return Optional.of(userService.findByEmail(email)).map(PersonFindDto::userTopPersonFindDtoMapper).orElse(null);
    }

    @GetMapping("/finAllReqParam")
    public List<PersonFindDto> findAllReq(@RequestParam Map<String, String> stringMap) {
        List<PersonFindDto> findDtos = new ArrayList<>();
        findDtos.addAll(expertService.findBy(stringMap).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        findDtos.addAll(userService.findBy(stringMap).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return findDtos;
    }

    @GetMapping("/findAllExpertReq")
    public List<PersonFindDto> findAllExpertReq(@RequestParam Map<String, String> stringMap) {
        return expertService.findBy(stringMap).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();
    }

    @GetMapping("/findAllUserReq")
    public List<PersonFindDto> findAllUserReq(@RequestParam Map<String, String> stringMap) {
        return userService.findBy(stringMap).stream()
                .map((PersonFindDto::userTopPersonFindDtoMapper)).toList();
    }

    @GetMapping("/userDetail/{id}")
    public Person userDetail(@PathVariable Long id) {
        Person byId = userService.findById(id);
        return userService.userDetail(byId);
    }

    @PostMapping("/offerInformation")
    public List<OfferDto> offerDtos(@RequestParam Map<String, String> map) {
        return offerService.offerSpecification(map).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    @PostMapping("/orderInformation")
    public List<Person> orderDtos(@RequestParam Map<String, String> map) {
        return userService.userSpecification(map).stream()
                .toList();
    }

    @GetMapping("/countOfOrder/{userId}")
    public int countOfOrder(@PathVariable Long userId) {
        return orderService.countOfOrdersByUserId(userId);
    }


    private AdminDto adminDtoMapper(Person admin) {
        return AdminDto.builder()
                .username(admin.getUsername())
                .email(admin.getEmail())
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
    public List<PersonFindDto> findByBasicService(@PathVariable Long basicServiceId) {
        return expertService.findByBasicService(basicServiceId).stream()
                .map(PersonFindDto::expertTopPersonFindDtoMapper).toList();
    }

    @GetMapping("/showOrderSuggestionOrSelection")
    public List<OrderDto> showOrderSuggestionOrSelection() {
        return orderService.showOrderSuggestionOrSelection().stream()
                .map(OrderDto::orderToOrderDtoMapper).toList();
    }

}