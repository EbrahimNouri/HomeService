package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.dto.*;
import ir.maktab.homeservice.entity.*;
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
        Admin admin = (Admin) authentication.getPrincipal();
        service.changePassword(admin, password);
    }

    @GetMapping() //checked
    public AdminDto admin(Authentication authentication) {
        Admin admin = (Admin) authentication.getPrincipal();
        return AdminDto.adminDtoMapper(admin);
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

    @GetMapping("/showAllNewExpert") //checked
    public List<PersonFindDto> showAllNewExpert() {

        return expertService.showAllNewExperts().stream()
                .map(PersonFindDto::expertTopPersonFindDtoMapper).toList();

    }

    @PutMapping("/descriptionChange") //checked
    public void descriptionChange(@RequestParam Long typeServiceId, String description) {
        typeServiceService.descriptionChange(typeServiceId, description);
    }

    @PutMapping("/acceptExpert/{expertId}") //checked
    public void acceptExpert(@PathVariable Long expertId) {
        Expert acceptExpert = expertService.findById(expertId);
        expertService.acceptExpert(acceptExpert);
    }

    @DeleteMapping("/removeExpertFromBasicService/{expertId}") //checked
    public void removeExpertFromBasicService(@PathVariable Long expertId) {
        expertTypeServiceService.removeExpertFromBasicService(expertId);
    }

    @PostMapping("/addExpertToTypeService")//checked
    public void addExpertToTypeService(@RequestParam Long expert, Long typeService) {
        expertTypeServiceService.addExpertToTypeService
                (findExpertTypeServiceByDto(expert, typeService));
    }

    @DeleteMapping("/removeExpertFromTypeService") //checked
    public void removeExpertFromTypeService(@RequestParam Long expert, Long typeService) {

        expertTypeServiceService.removeExpertFromTypeService
                (findExpertTypeServiceByDto(expert, typeService));
    }


    @GetMapping("findExpertTypeServiceById") //checked
    public ExpertTypeServiceDto findExpertTypeServiceById(@RequestParam Long expert
            , Long typeService) {


        ExpertTypeService expertTypeService = expertTypeServiceService.findById
                (expert, typeService);
        Expert expert1 = expertTypeService.getExpert();
        TypeService typeService1 = expertTypeService.getTypeService();

        return ExpertTypeServiceDto.expertTypeServiceToDtoMapper(expert1, typeService1);
    }


    // TODO: 1/15/2023 AD
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

    @GetMapping("/showAllTypeService/{id}") //checked
    public List<TypeServiceDto> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceService.showTypeServices(id).stream()
                .map((TypeServiceDto::typeServiceToTypeServiceDto)).toList();
    }

    @PostMapping("/addTypeService")//checked
    public void addTypeService(@RequestParam String name, Long baseId, Double price, String description) {

        TypeService typeService = TypeService.builder()
                .description(description)
                .subService(name)
                .basicService(basicServicesService.findById(baseId))
                .basePrice(price)
                .build();

        typeServiceService.addSubService(typeService);
    }


    @PostMapping("/addSubService")//checked
    public void addSubService(@RequestBody TypeServiceDto typeServiceDto) {

        BasicService basicService = basicServicesService.findById(typeServiceDto.getBaseServiceId());

        typeServiceService.addSubService(TypeService.builder()
                .basicService(basicService)
                .subService(typeServiceDto.getName())
                .basePrice(typeServiceDto.getPrice())
                .build());
    }

    @PutMapping("/paymentPriceChange")//checked
    public void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto) {
        TypeService typeService = typeServiceService.findById(paymentPriceChangeDto.getTypeServiceId());

        typeServiceService.paymentPriceChange(typeService, paymentPriceChangeDto.getPrice());
    }

    @GetMapping("/showTypeServices/{basicServiceId}") //checked
    public List<TypeServiceDto> showTypeServices(@PathVariable Long basicServiceId) {
        return typeServiceService.showTypeServices(basicServiceId)
                .stream().map(TypeServiceDto::typeServiceToTypeServiceDto).toList();
    }

    @GetMapping("/all") //checked
    public List<PersonFindDto> showAll() {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findAll().stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        all.addAll(userService.findAll().stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return all;
    }

    @GetMapping("/findByFirstName") //checked
    public List<PersonFindDto> findByFirstNameAll(@RequestParam String firstname) {
        List<PersonFindDto> all = new ArrayList<>();
        all.addAll(expertService.findByFirstName(firstname).stream()
                .map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        all.addAll(userService.findByFirstname(firstname).stream()
                .map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return all;
    }


    @GetMapping("/findByFirstName/allExperts/{firstname}") //checked
    public List<PersonFindDto> findByFirstnameExpert(@PathVariable String firstname) {

        return expertService.findByFirstName(firstname).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByFirstName/allUsers/{firstname}") //checked
    public List<PersonFindDto> findByFirstnameUser(@PathVariable String firstname) {
        return userService.findByFirstname(firstname).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByLastnameAll")//checked
    public List<PersonFindDto> findByLastName(@RequestParam String lastname) {

        List<PersonFindDto> all = new ArrayList<>();

        all.addAll(expertService.findByLastName(lastname)
                .stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());

        all.addAll(userService.findByLastname(lastname)
                .stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());

        return all;
    }

    @GetMapping("/findByLastname/allExperts") //checked
    public List<PersonFindDto> findByLastnameExpert(@RequestParam String lastname) {

        return expertService.findByLastName(lastname).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByLastname/allUsers")//checked
    public List<PersonFindDto> findByLastnameUser(@RequestParam String lastname) {
        return userService.findByLastname(lastname).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList();

    }

    @GetMapping("/findByEmailAll")//checked
    public PersonFindDto[] findByEmailExpert(@RequestParam String email) {
        PersonFindDto[] persons = new PersonFindDto[2];
        try {
            persons[0] = Optional.of(expertService.findByEmail(email)).map(PersonFindDto::expertTopPersonFindDtoMapper)
                    .orElse(null);
        } catch (Exception ignore) {
        }

        try {
            persons[1] = Optional.of(userService.findByEmail(email)).map(PersonFindDto::userTopPersonFindDtoMapper)
                    .orElse(null);
        } catch (Exception ignore) {
        }

        return persons;
    }

    @GetMapping("/findByEmailExpert")//checked
    public PersonFindDto findByEmailAll(@RequestParam String email) {
        return Optional.of(expertService.findByEmail(email)).map(PersonFindDto::expertTopPersonFindDtoMapper).orElse(null);
    }

    @GetMapping("/findByEmailUser")//checked
    public PersonFindDto findByEmailUser(@RequestParam String email) {
        return Optional.of(userService.findByEmail(email)).map(PersonFindDto::userTopPersonFindDtoMapper).orElse(null);
    }

    @GetMapping("/finAllReqParam")//checked
    public List<PersonFindDto> findAllReq(@RequestParam Map<String, String> stringMap) {
        List<PersonFindDto> findDtos = new ArrayList<>();
        findDtos.addAll(expertService.findBy(stringMap).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList());
        findDtos.addAll(userService.findBy(stringMap).stream().map(PersonFindDto::userTopPersonFindDtoMapper).toList());
        return findDtos;
    }

    @GetMapping("/findAllExpertReq")//checked
    public List<PersonFindDto> findAllExpertReq(@RequestParam Map<String, String> stringMap) {
        return expertService.findBy(stringMap).stream().map(PersonFindDto::expertTopPersonFindDtoMapper).toList();
    }

    @GetMapping("/findAllUserReq")//checked
    public List<PersonFindDto> findAllUserReq(@RequestParam Map<String, String> stringMap) {
        return userService.findBy(stringMap).stream()
                .map((PersonFindDto::userTopPersonFindDtoMapper)).toList();
    }

    @GetMapping("/userDetail/{id}")//checked
    public PersonFindDto userDetail(@PathVariable Long id) {
        User byId = userService.findById(id);
        return PersonFindDto.userTopPersonFindDtoMapper(userService.userDetail(byId));
    }

    @GetMapping("/offerInformation")
    public List<OfferDto> offerDtos(@RequestParam Map<String, String> map) {
        return offerService.offerSpecification(map).stream()
                .map(OfferDto::offerToOfferDtoMapping).toList();
    }

    //checked
    @GetMapping("/orderInformation")
    public List<PersonFindDto> orderDtos(@RequestParam Map<String, String> map) {

        return userService.userSpecification(map).stream()
                .map(PersonFindDto::userTopPersonFindDtoMapper).toList();
    }

    @GetMapping("/countOfOrder/{userId}")    //checked

    public int countOfOrder(@PathVariable Long userId) {
        return orderService.countOfOrdersByUserId(userId);
    }


    @GetMapping("/findByExpertsBasicService/{basicServiceId}")    //checked
    public List<PersonFindDto> findByBasicService(@PathVariable Long basicServiceId) {
        return expertService.findByBasicService(basicServiceId).stream()
                .map(PersonFindDto::expertTopPersonFindDtoMapper).toList();
    }

    @GetMapping("/showOrderSuggestionOrSelection")    //checked
    public List<OrderDto> showOrderSuggestionOrSelection() {
        return orderService.showOrderSuggestionOrSelection().stream()
                .map(OrderDto::orderToOrderDtoMapper).toList();
    }

    private ExpertTypeService findExpertTypeServiceByDto(Long expertId, Long typeServiceId) {

        Expert expert = expertService.findById(expertId);

        TypeService typeService = typeServiceService.findById(typeServiceId);

        return new ExpertTypeService(expert, typeService);
    }
}