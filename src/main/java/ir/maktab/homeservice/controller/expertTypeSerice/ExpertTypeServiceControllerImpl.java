package ir.maktab.homeservice.controller.expertTypeSerice;

import ir.maktab.homeservice.dto.ExpertTypeServiceDto;
import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.entity.id.ExpertTypeServiceId;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.service.expert.ExpertService;
import ir.maktab.homeservice.service.expertTypeSerice.ExpertTypeServiceService;
import ir.maktab.homeservice.service.typeService.TypeServiceService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Log4j2
@AllArgsConstructor
@RequestMapping("api/v1/expertService")
class ExpertTypeServiceControllerImpl implements ExpertTypeServiceController {
    private ExpertTypeServiceService service;
    private ExpertService expertService;
    private TypeServiceService typeServiceService;

    @Override
    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    public void removeExpertFromBasicService(@PathVariable Long expertId) {
        Expert expert = expertService.findById(expertId)
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));

        service.removeExpertFromBasicService(expert);
    }

    @PostMapping("/addExpertToTypeService")
    @Override
    public void addExpertToTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {
        service.addExpertToTypeService(findExpertTypeServiceByDto(expertTypeServiceDto));
    }

    @Override
    @DeleteMapping("/removeExpertFromTypeService/")
    public void removeExpertFromTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {

        service.removeExpertFromTypeService(findExpertTypeServiceByDto(expertTypeServiceDto));
    }


    @Override
    @GetMapping("/findById/")
    public ResponseEntity<ExpertTypeService> findById(@RequestBody ExpertTypeServiceDto expertTypeServiceDto) {
        Expert expert = findExpertTypeServiceByDto(expertTypeServiceDto).getExpert();
        TypeService typeService = findExpertTypeServiceByDto(expertTypeServiceDto).getTypeService();

        return service.findById(new ExpertTypeServiceId(expert, typeService)).map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    private ExpertTypeService findExpertTypeServiceByDto(ExpertTypeServiceDto expertTypeServiceDto) {
        Expert expert = expertService.findById(expertTypeServiceDto.getExpertId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));

        TypeService typeService = typeServiceService.findById(expertTypeServiceDto.getTypeServiceId())
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found"));

        return new ExpertTypeService(expert, typeService);
    }
}