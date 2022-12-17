package ir.maktab.homeservice.controller.basicServices;


import ir.maktab.homeservice.dto.BasicServiceDto;
import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Log4j2
@RequiredArgsConstructor
@RequestMapping("api/v1/basicService")
public class BasicServicesControllerImpl implements BasicServicesController {
    private final BasicServiceRepository basicServiceRepository;

    private final BasicServicesService basicServicesService;

    @Override
    @PostMapping("/add")
    public void addBasicService(@RequestBody @Valid BasicService basicService) {
        basicServicesService.addBasicService(basicService);
    }

    @Override
    @GetMapping("/all")
    public List<BasicServiceDto> showAllBasicServices() {
        return basicServiceRepository.findAll().stream()
                .map(bs -> new BasicServiceDto(bs.getId(), bs.getName())).toList();
//        var xx = basicServicesService.showAllBasicService();
//        List<BasicServiceDto> returned = new ArrayList<>();
//
//        for (BasicService bts : xx) {
//            returned.add(BasicServiceMapper.INSTANCE.entityToDTO(bts));
//        }
//        return returned;
//        returned.add(BasicServiceMapper.INSTANCE(basicService));
//        List<BasicService> basicServices = basicServicesService.findAll();
//        for (BasicService b: basicServices) {
//            BasicServiceDto destination = sourceToDestination();
    }


    // TODO: 12/14/2022 AD this method not work

}




