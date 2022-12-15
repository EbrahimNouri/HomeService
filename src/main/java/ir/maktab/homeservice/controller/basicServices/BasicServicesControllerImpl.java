package ir.maktab.homeservice.controller.basicServices;

import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.repository.basicService.BasicServiceRepository;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
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
    @PostMapping("/addBasicService")
    public void addBasicService(@RequestBody BasicService basicService) {
        basicServicesService.addBasicService(basicService);
    }

    @Override
    @GetMapping("/all")
    public List<BasicServiceDto> showAllBasicServices() {
        var x = basicServiceRepository.findAll().stream()
                .map(bs -> new BasicServiceDto(bs.getId(), bs.getName())).toList();

        List<BasicService> basicServices = basicServicesService.findAll();
        for (BasicService b: basicServices) {
//            BasicServiceDto destination = sourceToDestination();
        }


        return x;
        // TODO: 12/14/2022 AD this method not work
    }

    @AllArgsConstructor
    public static class BasicServiceDto {
        private Long dtoId;
        private String dtoName;
    }

}
