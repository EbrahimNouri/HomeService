package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.transaction.TransactionRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminControllerImpl implements AdminController {
    private BasicServicesService basicServicesService;
    private TypeServiceService typeServiceService;
    private final TypeServiceRepository typeServiceRepository;
    private final BasicServiceRepository basicServiceRepository;
    private final TransactionRepository transactionRepository;







//    @GetMapping("findBasicServiceById/{id}")
//    public ResponseEntity<BasicService> findBasicServiceById(@PathVariable Long id) {
//        BasicService basicService = basicServiceRepository.findById(id).get();
//        basicService.setTypeServices(null);
//        Optional<BasicService> basicService1 = Optional.of(basicService);
//        return basicServiceRepository.findById(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
//    }
}