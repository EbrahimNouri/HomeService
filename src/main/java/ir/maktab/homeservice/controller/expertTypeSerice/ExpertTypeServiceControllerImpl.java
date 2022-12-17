package ir.maktab.homeservice.controller.expertTypeSerice;

import ir.maktab.homeservice.entity.Expert;
import ir.maktab.homeservice.entity.ExpertTypeService;
import ir.maktab.homeservice.entity.TypeService;
import ir.maktab.homeservice.exception.CustomExceptionNotFind;
import ir.maktab.homeservice.repository.expert.ExpertRepository;
import ir.maktab.homeservice.repository.expertTypeService.ExpertTypeServiceRepository;
import ir.maktab.homeservice.repository.expertUser.ExpertUserRepository;
import ir.maktab.homeservice.repository.typeService.TypeServiceRepository;
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
@RequestMapping("api/v1/expertTypeService")
class ExpertTypeServiceControllerImpl implements ExpertTypeServiceController {
    private final ExpertUserRepository expertUserRepository;
    private final TypeServiceRepository typeServiceRepository;
    private final ExpertTypeServiceRepository expertTypeServiceRepository;

    private ExpertTypeServiceService service;
    private ExpertRepository expertRepository;
    private ExpertService expertService;
    private TypeServiceService typeServiceService;

    @DeleteMapping("/removeExpertFromBasicService/{deleteId}")
    @Override
    public void removeExpertFromBasicService(@PathVariable Long deleteId) {
        var expert = expertRepository.findById(deleteId)
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));

        service.removeExpertFromBasicService(expert);
    }

    @PostMapping("/addExpertToTypeService")
    @Override
    public void addExpertToTypeService(@RequestBody ExpertTypeService expertTypeService) {

        service.addExpertToTypeService(findAndSet(expertTypeService));
    }

    @Override
    @DeleteMapping("/removeExpertFromTypeService")
    public void removeExpertFromTypeService(@RequestBody ExpertTypeService expertTypeService) {

        service.removeExpertFromTypeService(findAndSet(expertTypeService));
    }

    @Override
    @GetMapping("findById/{expId}/{typId}")
    public ResponseEntity<ExpertTypeService> findById(@PathVariable Long expId, @PathVariable Long typId) {
        ExpertTypeService expertTypeService = new ExpertTypeService();

        Expert expert = new Expert();
        TypeService typeService = new TypeService();

        expert.setId(expId);
        typeService.setId(typId);

        expertTypeService.setExpert(expert);
        expertTypeService.setTypeService(typeService);

        return ResponseEntity.ok(findAndSet(expertTypeService));
    }

    private ExpertTypeService findAndSet(ExpertTypeService expertTypeService) {
        Expert expert = expertRepository.findById(expertTypeService.getExpert().getId())
                .orElseThrow(() -> new CustomExceptionNotFind("expert not found"));
        expertTypeService.setExpert(expert);

        TypeService typeService = typeServiceRepository.findById(expertTypeService.getExpert().getId())
                .orElseThrow(() -> new CustomExceptionNotFind("type service not found"));
        expertTypeService.setTypeService(typeService);
        return expertTypeService;
    }

}