package ir.maktab.homeservice.controller.typeService;


import ir.maktab.homeservice.dto.PaymentPriceChangeDto;
import ir.maktab.homeservice.dto.TypeServiceDto;
import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
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
    private final BasicServiceRepository basicServiceRepository;
    private final TypeServiceRepository typeServiceRepository;
    private final BasicServicesService basicServicesService;
    private final TypeServiceService service;


    @PostMapping("/addTypeService")
    public void addTypeService(@RequestBody TypeServiceDto typeServiceDto) {

        TypeService typeService = TypeService.builder()
                .subService(typeServiceDto.getName())
                .basicService(basicServiceRepository.findById(typeServiceDto.getBaseServiceId()).orElseThrow(
                        () ->  new CustomExceptionNotFind("basic service not found")))
                .basePrice(typeServiceDto.getPrice())
                .build();

        service.addSubService(typeService);
    }

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeService> findByBasicServiceId(@PathVariable Long id) {
        return typeServiceRepository.findByBasicServiceId(id);
    }

    @PostMapping("/addSubService")
    @Override
    public void addSubService(@RequestBody TypeServiceDto typeServiceDto) {

       BasicService basicService = basicServicesService.findById(typeServiceDto.getBaseServiceId())
               .orElseThrow(() -> new CustomExceptionNotFind("basic service not found."));

       service.addSubService(TypeService.builder()
               .basicService(basicService)
               .subService(typeServiceDto.getName())
               .basePrice(typeServiceDto.getPrice())
               .build());
    }

    @Override
    @PutMapping("/paymentPriceChange")
    public void paymentPriceChange(@RequestBody PaymentPriceChangeDto paymentPriceChangeDto) {
        TypeService typeService = service.findById(paymentPriceChangeDto.getTypeServiceId())
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found."));

        service.paymentPriceChange(typeService, paymentPriceChangeDto.getPrice());
    }

    @Override
    @GetMapping("/showTypeServices/{basicServiceId}")
    public List<TypeService> showTypeServices(@PathVariable Long basicServiceId) {
        return service.showTypeServices(basicServiceId);
    }
}
