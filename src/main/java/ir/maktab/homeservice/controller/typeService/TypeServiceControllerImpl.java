package ir.maktab.homeservice.controller.typeService;


import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@Log4j2
@RequestMapping("/api/v1/typeService")
public class TypeServiceControllerImpl implements TypeServiceController {
    private final TypeServiceRepository typeServiceRepository;

    private final TypeServiceService typeServiceService;


    @PostMapping("/addTypeService")
    public void addTypeService(@RequestBody TypeService typeService) {
        typeServiceService.addSubService(typeService);
        System.out.println(typeService);
    }

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeService> showAllBasicServicesByExpertId(@PathVariable Long id) {
        return typeServiceRepository.findByBasicServiceId(id);
    }

    @Override
    public void addSubService(TypeService typeService) {

    }

    @Override
    public void paymentPriceChange(TypeService typeService, double price) {

    }

    @Override
    public List<TypeService> showTypeServices(Long basicServiceId) {
        return null;
    }
}
