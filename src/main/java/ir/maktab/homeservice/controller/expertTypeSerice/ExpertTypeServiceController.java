package ir.maktab.homeservice.controller.expertTypeSerice;


import ir.maktab.homeservice.dto.ExpertTypeServiceDto;
import ir.maktab.homeservice.entity.ExpertTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface ExpertTypeServiceController {
    @DeleteMapping("/removeExpertFromBasicService/{expertId}")
    void removeExpertFromBasicService(@PathVariable Long expertId);

    void addExpertToTypeService(ExpertTypeServiceDto expertTypeServiceDto);


    @DeleteMapping("/removeExpertFromTypeService/")
    void removeExpertFromTypeService(@RequestBody ExpertTypeServiceDto expertTypeServiceDto);

    @GetMapping("/findById/")
    public ResponseEntity<ExpertTypeService> findById(@RequestBody ExpertTypeServiceDto expertTypeServiceDto);

}
