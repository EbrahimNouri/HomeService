package ir.maktab.homeservice.controller.admin;


import ir.maktab.homeservice.entity.BasicService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.service.basicServices.BasicServicesService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
@AllArgsConstructor
public class AdminControllerImpl implements AdminController {
    private BasicServicesService basicServicesService;
    private TypeServiceService typeServiceService;

    @PostMapping("/addBasicService")
    public void addBasicService(@RequestBody BasicService basicService){
        basicServicesService.addBasicService(basicService);
    }

    @GetMapping("/showAllBasicServices")
    public List<BasicService> showAllBasicServices(){

        return basicServicesService.showAllBasicService();
        // TODO: 12/14/2022 AD this method not work
    }

    @PostMapping("/addTypeService")
    public void addTypeService(@RequestBody TypeService typeService){
        typeServiceService.addSubService(typeService);
        System.out.println(typeService);
    }

    @GetMapping("/showAllTypeService/{id}")
    public List<TypeService> showAllBasicServices(@PathVariable Long id){
        return typeServiceService.showTypeServices(id);
    }
}