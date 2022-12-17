package ir.maktab.homeservice.controller.expertTypeSerice;


import ir.maktab.homeservice.entity.ExpertTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

public interface ExpertTypeServiceController {
    void removeExpertFromBasicService(Long expert);

    void addExpertToTypeService(ExpertTypeService expertTypeService);

    void removeExpertFromTypeService(ExpertTypeService expertTypeService);


    @GetMapping("findById/{expId}/{typId}")
    ResponseEntity<ExpertTypeService> findById(@PathVariable Long expId, @PathVariable Long typId);
}
